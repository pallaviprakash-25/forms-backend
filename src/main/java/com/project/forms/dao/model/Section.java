package com.project.forms.dao.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class Section {
    private String sectionId;

    @NotNull
    @Size(min=5, max=256, message = "Section name must be between 5 and 256 characters long")
    private String name;

    @Size(min=5, max=256, message = "Section description must be between 5 and 256 characters long")
    private String description;

    private boolean shuffle;

    @Valid
    @Size(max=30, message = "There can be a maximum of 30 questions in a section")
    private List<Question> questions;

}
