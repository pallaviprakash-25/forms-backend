package com.project.forms.dao.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum QuestionType {
    MULTI_SELECT("multiSelect"),
    SINGLE_SELECT("singleSelect");

    private final String jsonValue;

    QuestionType(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    @JsonValue
    public String getJsonValue() {
        return jsonValue;
    }

    @JsonCreator
    public static QuestionType forValue(String value) {
        for (QuestionType type : values()) {
            if (type.getJsonValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid form type: " + value);
    }
}
