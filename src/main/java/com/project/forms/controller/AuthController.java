package com.project.forms.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.project.forms.dao.response.TokenDetails;
import com.project.forms.dao.response.UrlDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;

import static com.project.forms.utils.APIConstants.AUTH_CALLBACK_URL;
import static com.project.forms.utils.APIConstants.AUTH_URL;
import static com.project.forms.utils.Constants.*;

@RestController
@Tag(name = "Auth", description = "Auth APIs")
public class AuthController {

    @Value("${app.frontend.url}")
    private String frontEndUrl;

    @Value("${spring.security.oauth2.resourceserver.opaque-token.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.resourceserver.opaque-token.client-secret}")
    private String clientSecret;

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

    @Operation(summary = "Handles OAuth callback", description = "Handles the OAuth callback, where the authorization " +
            "code is exchanged for an access token.")
    @GetMapping(value = AUTH_CALLBACK_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDetails> callback(@RequestParam(CODE) final String code) {
        String token;
        try {
            token = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    new GsonFactory(),
                    clientId,
                    clientSecret,
                    code,
                    frontEndUrl
            ).execute().getAccessToken();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new TokenDetails(token));
    }
}
