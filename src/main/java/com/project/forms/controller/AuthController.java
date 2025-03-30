package com.project.forms.controller;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.project.forms.config.GoogleJwtTokenService;
import com.project.forms.config.GuestJwtTokenService;
import com.project.forms.dao.response.TokenDetails;
import com.project.forms.dao.response.UrlDetails;
import com.project.forms.exceptionHandling.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;

import static com.project.forms.utils.APIConstants.*;
import static com.project.forms.utils.Constants.*;

@RestController
@Tag(name = "Auth", description = "Auth APIs")
public class AuthController {

    @Value("${app.frontend.url}")
    private String frontEndUrl;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Autowired
    private GoogleJwtTokenService googleJwtTokenService;

    @Autowired
    private GuestJwtTokenService guestTokenService;

    private static final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final GsonFactory JSON_FACTORY = new GsonFactory();


    @Operation(summary = "Get Google OAuth authorization URL", description = "Generates a Google OAuth authorization " +
            "URL that the client can use to initiate the authentication process. The generated URL directs the user " +
            "to Google's OAuth consent screen")
    @GetMapping(value = AUTH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UrlDetails> auth() {
        final String url = new GoogleAuthorizationCodeRequestUrl(
                clientId,
                frontEndUrl,
                Arrays.asList(EMAIL, PROFILE, OPENID)
        ).build();
        return ResponseEntity.ok(new UrlDetails(url));
    }

    @Operation(summary = "Handles OAuth callback", description = "Exchanges the authorization code for an ID token, " +
            "verifies it, and generates a custom JWT for the authenticated user.")
    @GetMapping(value = AUTH_CALLBACK_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity callback(@RequestParam(CODE) String code) {
        try {
            GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    new GsonFactory(),
                    clientId,
                    clientSecret,
                    code,
                    frontEndUrl
            ).execute();
            String idTokenString = tokenResponse.getIdToken();
            if (idTokenString == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("No ID token received"));
            }

            // Verify Google ID Token
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP_TRANSPORT, JSON_FACTORY)
                    .setAudience(Collections.singletonList(clientId))
                    .setIssuer(GOOGLE_TOKEN_ISSUER) // Ensures token is from Google
                    .build();
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Invalid ID token"));
            }

            // Extract user details
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Generate our own JWT
            String jwtToken = googleJwtTokenService.generateToken(payload);
            return ResponseEntity.ok(new TokenDetails(jwtToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Invalid authorization code"));
        }
    }

    @Operation(summary = "Get Guest JWT", description = "Generates a custom JWT for Guest users")
    @GetMapping(value = AUTH_GUEST_TOKEN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDetails> getGuestToken() {
        String token = guestTokenService.generateGuestToken();
        return ResponseEntity.ok(new TokenDetails(token));
    }
}
