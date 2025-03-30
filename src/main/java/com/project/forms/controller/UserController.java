package com.project.forms.controller;

import com.project.forms.dao.response.UserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.project.forms.utils.APIConstants.USER;
import static com.project.forms.utils.CommonUtils.getRole;
import static com.project.forms.utils.Constants.*;

@RestController
@Tag(name = "User", description = "User Details API")
public class UserController {

    @Operation(summary = "Get current user details", description = "Get current user details")
    @GetMapping(USER)
    public ResponseEntity<UserDetails> getUserInfo(@AuthenticationPrincipal final Jwt token) {
        String userId, name, email, picture;
        if (getRole(token).equals(ROLE_GUEST)) {
            userId = GUEST;
            name = GUEST;
            email = GUEST;
            picture = "#";
        } else {
            userId = token.getSubject();
            name = token.getClaim(NAME);
            email = token.getClaim(EMAIL);
            picture = token.getClaim(PICTURE);
        }
        UserDetails userDetails = new UserDetails(userId, name, email, picture);
        return ResponseEntity.ok(userDetails);
    }
}
