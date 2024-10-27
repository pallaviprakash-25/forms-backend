package com.project.forms.controller;

import com.project.forms.dao.model.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class UserController {

    @GetMapping("/user")
    public ResponseEntity<UserDetails> getUserInfo(@AuthenticationPrincipal final OAuth2User user) {
        // Get user details from the authentication token
        String name = user.getAttributes().get("name").toString();
        String email = user.getAttributes().get("email").toString();
        String pictureUrl = user.getAttributes().get("picture").toString();

        // Return user information as a string or JSON object
        log.info(String.format("User Info: Name: %s, Email: %s, Picture: %s", name, email, pictureUrl));

        UserDetails userDetails = new UserDetails(name, email, pictureUrl);
        return ResponseEntity.ok(userDetails);
    }
}
