package com.project.forms.dao.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.forms.dao.model.Form;
import com.project.forms.dao.model.Section;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormResponse {
    private String formId;
    private String formName;
    private List<Section> sections;

    public static FormResponse from(final Form form) {
        final FormResponse formResponse = new FormResponse();
        BeanUtils.copyProperties(form, formResponse);
        return formResponse;
    }
}
