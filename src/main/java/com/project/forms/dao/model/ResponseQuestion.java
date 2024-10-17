package com.project.forms.dao.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ResponseQuestion {
    @NotNull
    private String id;

    @Size(min=1, max=30, message = "A question must have a minimum of 1 and a maximum of 30 options")
    private List<String> options;
}
