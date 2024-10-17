package com.project.forms.dao.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ResponseSection {
    @NotNull
    private String id;

    @Valid
    @Size(max=30, message = "A section can have a maximum of 30 questions")
    private List<ResponseQuestion> questions;
}
