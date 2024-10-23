package com.project.forms.dao.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class Question {
    private String id;

    @NotBlank
    @Size(max=256, message = "Question label can have a maximum of 256 characters")
    private String label;

    @NotBlank
    @Pattern(regexp = "multi_select|single_select", message = "Question type must be multi_select or single_select")
    private String type;

    private boolean shuffle;

    private boolean required;

    @Valid
    @NotNull
    @Size(min=1, max=30, message = "A question must have a minimum of 1 and a maximum of 30 options")
    private List<Option> options;
}
