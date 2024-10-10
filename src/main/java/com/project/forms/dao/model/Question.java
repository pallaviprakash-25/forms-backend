package com.project.forms.dao.model;

import com.project.forms.dao.enums.QuestionType;
import lombok.Data;

import java.util.List;

@Data
public class Question {
    private String id;
    private String label;
    private QuestionType type;
    private boolean shuffle;
    private boolean required;
    private List<Option> options;

}
