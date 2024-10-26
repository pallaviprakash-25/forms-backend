package com.project.forms.dao.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.forms.dao.model.ResponseSection;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormResponseSaveRequest {
    @NotNull
    private String formId;

    private String submittedBy;

    @Valid
    @Size(max=30, message = "A form can have a maximum of 30 sections")
    private List<ResponseSection> sections;
}
