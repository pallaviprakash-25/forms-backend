package com.project.forms.dao.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.forms.dao.model.Audit;
import com.project.forms.dao.model.Section;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormCreateUpdateRequest {
    private String formId;

    @NotNull
    @Size(max=256, message = "Form name can have a maximum of 256 characters")
    private String formName;

    @Valid
    @Size(max=30, message = "A form can have a maximum of 30 sections")
    private List<Section> sections;

    private Audit audit;
}
