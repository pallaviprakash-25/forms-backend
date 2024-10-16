package com.project.forms.dao.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Option {
    private String id;

    @NotNull
    @Size(max=256, message = "Option label can have a maximum of 256 characters")
    private String label;
}
