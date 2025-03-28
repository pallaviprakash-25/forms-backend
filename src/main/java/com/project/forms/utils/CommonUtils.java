package com.project.forms.utils;

import org.springframework.security.oauth2.core.user.OAuth2User;

import static com.project.forms.utils.Constants.EMAIL;
import static com.project.forms.utils.Constants.MOCK_USER;

public class CommonUtils {
    public static String getUserId(final OAuth2User user) {
        return user != null ? user.getAttributes().get(EMAIL).toString() : MOCK_USER;
    }
}
