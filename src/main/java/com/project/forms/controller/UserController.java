package com.project.forms.controller;

import com.project.forms.dao.response.UserInfo;
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
    public ResponseEntity<UserInfo> getUserInfo(@AuthenticationPrincipal final OAuth2User user) {
        String name, email, pictureUrl;
        if (getUserId(user).equals(MOCK_USER)) {
            name = MOCK_USER;
            email = MOCK_USER;
            pictureUrl = "#";
        } else {
            name = user.getAttributes().get(NAME).toString();
            email = user.getAttributes().get(EMAIL).toString();
            pictureUrl = user.getAttributes().get(PICTURE).toString();
        }

        UserInfo userInfo = new UserInfo(name, email, pictureUrl);
        return ResponseEntity.ok(userInfo);
    }
}
