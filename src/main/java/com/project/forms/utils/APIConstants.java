package com.project.forms.utils;

public interface APIConstants {
    String API_VERSION_1 = "/api/v1";
    String FORMS = "/forms";
    String ID = "/{id}";
    String USER = "/user";

    String FORMS_PATH = API_VERSION_1 + FORMS;
    String USER_ID_PATH = USER + ID;
}
