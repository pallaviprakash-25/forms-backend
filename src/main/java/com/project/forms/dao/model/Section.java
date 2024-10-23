package com.project.forms.dao.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class Section {
    private String id;

    @NotBlank
    @Size(max=256, message = "Section name can have a maximum of 256 characters")
    private String name;

    @Size(max=256, message = "Section description can have a maximum of 256 characters")
    @Pattern(regexp = "^\\s*\\S.*$", message = "Section description must not be blank")  // Ensures the field is not blank if present
    private String description;

    private boolean shuffle;

    @Valid
    @Size(max=30, message = "A section can have a maximum of 30 questions")
    private List<Question> questions;

}
