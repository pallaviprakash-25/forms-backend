package com.project.forms.controller;

import com.project.forms.dao.response.UserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.project.forms.utils.APIConstants.USER;
import static com.project.forms.utils.CommonUtils.getUserId;
import static com.project.forms.utils.Constants.*;

@RestController
public class UserController {

    @GetMapping(USER)
    public ResponseEntity<UserDetails> getUserInfo(@AuthenticationPrincipal final OAuth2User user) {
        String userId, name, email, pictureUrl;
        if (getUserId(user).equals(MOCK_USER)) {
            userId = MOCK_USER;
            name = MOCK_USER;
            email = MOCK_USER;
            pictureUrl = "#";
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
