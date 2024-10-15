package com.project.forms.manager;

import com.project.forms.dao.model.Form;
import com.project.forms.dao.request.FormCreateUpdateRequest;
import com.project.forms.dao.response.FormCreateUpdateResponse;
import com.project.forms.dao.response.FormResponse;
import com.project.forms.repository.FormsRepository;
import com.project.forms.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class FormsManager {

    @Autowired
    private FormsRepository formsRepository;

    /**
     * Method to fetch form details based on formId
     *
     * @param formId ID of the form
     * @return {@link FormResponse} instance
     * @throws BadRequestException
     */
    public FormResponse getFormById(final String formId) throws BadRequestException {
        final Optional<Form> form = formsRepository.findById(formId);
        if (form.isEmpty()) {
            log.error("Form with ID {} does not exist", formId);
            throw new BadRequestException("Form ID does not exist");
        }
        return FormResponse.from(form.get());
    }

    /**
     * Method to create/update form
     *
     * @param request {@link FormCreateUpdateRequest} instance
     * @return {@link FormCreateUpdateResponse} instance
     */
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
        setIds(request);

        final Form form = new Form();
        BeanUtils.copyProperties(request, form);
        final Form response = formsRepository.save(form);
        return FormCreateUpdateResponse.from(response);
    }

    /**
     * Helper method to set section, question and option IDs if missing
     *
     * @param request {@link FormCreateUpdateRequest} instance
     */
    private static void setIds(final FormCreateUpdateRequest request) {
        if (!CollectionUtils.isEmpty(request.getSections())) {
            request.getSections().forEach(section -> {
                if (StringUtils.isBlank(section.getId())) {
                    section.setId(Utils.generateRandomId());
                }
                if (!CollectionUtils.isEmpty(section.getQuestions())) {
                    section.getQuestions().forEach(question -> {
                        if (StringUtils.isBlank(question.getId())) {
                            question.setId(Utils.generateRandomId());
                        }
                        if (!CollectionUtils.isEmpty(question.getOptions())) {
                            question.getOptions().forEach(option -> {
                                if (StringUtils.isBlank(option.getId())) {
                                    option.setId(Utils.generateRandomId());
                                }
                            });
                        }
                    });
                }
            });
        }
    }
}
