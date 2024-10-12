package com.project.forms.dao.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class Question {
    private String id;

    @NotNull
    @Size(min=5, max=256, message = "Question label must be between 5 and 256 characters long")
    private String label;

    @NotNull
    @Pattern(regexp = "multi_select|single_select", message = "Question type must be multi_select or single_select")
    private String type;

    private boolean shuffle;

    private boolean required;

    @Valid
    @Size(max=30, message = "There can be a maximum of 30 options for a question")
    private List<Option> options;
}
