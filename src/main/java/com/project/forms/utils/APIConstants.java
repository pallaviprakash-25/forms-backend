package com.project.forms.utils;

public interface APIConstants {
    String API_VERSION_1 = "/api/v1";
    String FORMS = "/forms";
    String ID = "/{id}";
    String USER = "/user";
    String PUBLISHED = "/published";
    String RESPONSE = "/response";
    String AUTH = "/auth";
    String URL = "/url";
    String CALLBACK = "/callback";

    String FORMS_PATH = API_VERSION_1 + FORMS;
    String PUBLISHED_FORM_BY_ID_PATH = PUBLISHED + ID;

    String FORM_RESPONSE_PATH = FORMS_PATH + RESPONSE;

    String AUTH_URL = AUTH + URL;
    String AUTH_CALLBACK_URL = AUTH + CALLBACK;

    String OAUTH2_USERINFO_PATH = "/oauth2/v3/userinfo";
}
