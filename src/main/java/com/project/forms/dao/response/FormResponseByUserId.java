package com.project.forms.dao.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.forms.dao.model.Form;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class FormResponseByUserId {
    private List<FormDetails> forms;

    public static FormResponseByUserId from(final List<Form> formsResponse) {
        final List<FormDetails> formDetailsList = formsResponse.stream().map(form -> {
            FormDetails details = new FormDetails();
            BeanUtils.copyProperties(form, details);
            details.setCreatedBy(form.getAudit().getCreatedBy());
            if (form.getAudit().getModifiedOn() != null) {
                details.setModifiedOn(form.getAudit().getModifiedOn());
            } else {
                details.setModifiedOn(form.getAudit().getCreatedOn());
            }
            return details;
        }).toList();
        return new FormResponseByUserId(formDetailsList);
    }

    @Data
    @NoArgsConstructor
    public static class FormDetails {
        private String formId;
        private String formName;
        private String createdBy;
        private Date modifiedOn;
    }
}
