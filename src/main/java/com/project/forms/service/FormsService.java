package com.project.forms.service;

import com.project.forms.dao.model.Form;
import com.project.forms.dao.request.FormCreateUpdateRequest;
import com.project.forms.dao.response.FormCreateUpdateResponse;
import com.project.forms.repository.FormsRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class FormsService {

    @Autowired
    private FormsRepository formsRepository;

    public FormCreateUpdateResponse createUpdateForm(final FormCreateUpdateRequest request) {
        final Date currentDate = Date.from(Instant.now());
        if (StringUtils.isAllEmpty(request.getFormId())) {
            // create request
            final String formId = UUID.randomUUID().toString();
            request.setFormId(formId);
            request.getAudit().setCreatedOn(currentDate);
            request.getAudit().setModifiedOn(currentDate);
        } else {
            // update request
            request.getAudit().setModifiedOn(currentDate);
        }
        final Form form = new Form();
        BeanUtils.copyProperties(request, form);
        Form x = formsRepository.save(form);

        return new FormCreateUpdateResponse(x.getFormId());
    }
}
