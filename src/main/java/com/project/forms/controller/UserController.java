package com.project.forms.controller;

import com.project.forms.dao.response.UserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.project.forms.utils.APIConstants.USER;
import static com.project.forms.utils.CommonUtils.getUserId;
import static com.project.forms.utils.Constants.*;

@RestController
@Tag(name = "User", description = "User Details API")
public class UserController {

    @Operation(summary = "Get current user details", description = "Get current user details")
    @GetMapping(USER)
    public ResponseEntity<UserDetails> getUserInfo(@AuthenticationPrincipal final OAuth2User user) {
        String userId, name, email, pictureUrl;
        if (getUserId(user).equals(GUEST_USER)) {
            userId = GUEST_USER;
            name = GUEST_USER;
            email = GUEST_USER;
            pictureUrl = "test";
        } else {
            userId = user.getAttributes().get(SUB).toString();
            name = user.getAttributes().get(NAME).toString();
            email = user.getAttributes().get(EMAIL).toString();
            pictureUrl = user.getAttributes().get(PICTURE).toString();
        }

        UserDetails userDetails = new UserDetails(userId, name, email, pictureUrl);
        return ResponseEntity.ok(userDetails);
    }
}
