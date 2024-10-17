package com.project.forms.dao.response;

import com.project.forms.dao.model.Form;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FormCreateUpdateResponse {
    private String formId;

    public static FormCreateUpdateResponse from(final Form form) {
        return new FormCreateUpdateResponse(form.getFormId());
    }
}
