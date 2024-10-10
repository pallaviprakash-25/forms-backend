package com.project.forms.dao.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.forms.dao.model.Audit;
import com.project.forms.dao.model.Section;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormCreateUpdateRequest {
    private String formId;
    private String formName;
    private Audit audit;
    private List<Section> sections;
}
