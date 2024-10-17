package com.project.forms.utils;

public interface APIConstants {
    String API_VERSION_1 = "/api/v1";
    String FORMS = "/forms";
    String ID = "/{id}";
    String USER = "/user";
    String PUBLISHED = "/published";
    String RESPONSE = "/response";

    String FORMS_PATH = API_VERSION_1 + FORMS;
    String USER_ID_PATH = USER + ID;
    String PUBLISHED_FORM_BY_ID_PATH = PUBLISHED + ID;

    String FORM_RESPONSE_PATH = FORMS_PATH + RESPONSE;
}
