package com.project.forms.dao.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Option {
    private String id;

    @NotNull
    @Size(min=5, max=256, message = "Option label must be between 5 and 256 characters long")
    private String label;
}
