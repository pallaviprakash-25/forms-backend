package com.project.forms.utils;

import com.project.forms.dao.model.Form;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

import static com.project.forms.utils.Constants.ROLE;

@Slf4j
public class CommonUtils {
    public static String getUserId(final Jwt token) {
        return token.getSubject();
    }
    public static String getRole(final Jwt token) {
        return token.getClaimAsString(ROLE);
    }

    public static void validateFormResponse(final List<Form> form, final String formId) throws BadRequestException {
        if (form.isEmpty()) {
            log.error("Form with ID {} does not exist", formId);
            throw new BadRequestException("Form ID does not exist");
        }
    }
}
