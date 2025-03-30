package com.project.forms.manager;

import com.project.forms.dao.model.Audit;
import com.project.forms.dao.model.Form;
import com.project.forms.dao.request.FormCreateUpdateRequest;
import com.project.forms.dao.response.FormCreateUpdateResponse;
import com.project.forms.dao.response.FormDetailsById;
import com.project.forms.dao.response.FormDetailsByUserId;
import com.project.forms.dao.response.PublishedFormDetailsById;
import com.project.forms.repository.FormsRepository;
import com.project.forms.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.project.forms.utils.Constants.ROLE_GUEST;

@Service
@Slf4j
public class FormsManager {

    @Autowired
    private FormsRepository formsRepository;

    /**
     * Method to create/update form
     *
     * @param request {@link FormCreateUpdateRequest} instance
     * @return {@link FormCreateUpdateResponse} instance
     */
    public FormCreateUpdateResponse createUpdateForm(final FormCreateUpdateRequest request,
                                                     final String userId) throws BadRequestException {
        final Date currentDate = Date.from(Instant.now());
        if (StringUtils.isAllEmpty(request.getFormId())) {
            // create request
            final String formId = UUID.randomUUID().toString();
            request.setFormId(formId);
            final Audit audit = new Audit();
            audit.setCreatedOn(currentDate);
            audit.setCreatedBy(userId);
            request.setAudit(audit);
        } else {
            // update request
            final List<Form> form = formsRepository.findFormByFormAndUserId(request.getFormId(), userId);
            CommonUtils.validateFormResponse(form, request.getFormId());
            request.getAudit().setModifiedOn(currentDate);
            request.getAudit().setModifiedBy(userId);
        }
        setIds(request);

        final Form form = new Form();
        BeanUtils.copyProperties(request, form);
        final Form response = formsRepository.save(form);
        return FormCreateUpdateResponse.from(response);
    }

    /**
     * Method to fetch form details based on formId
     *
     * @param formId ID of the form
     * @return {@link FormDetailsById} instance
     * @throws BadRequestException
     */
    public FormDetailsById getFormByFormAndUserId(final String formId, final String userId) throws BadRequestException {
        final List<Form> form = formsRepository.findFormByFormAndUserId(formId, userId);
        CommonUtils.validateFormResponse(form, formId);
        return FormDetailsById.from(form.get(0));
    }

    /**
     * Method to fetch details of all forms created by a user
     *
     * @param userId ID of the user whose forms need to be fetched
     * @return {@link FormDetailsByUserId} instance
     */
    public FormDetailsByUserId getFormByUserId(final String userId) {
        final List<Form> forms = formsRepository.findAllFormsByUserId(userId);
        return FormDetailsByUserId.from(forms);
    }

    /**
     * Method to fetch published form details based on formId
     *
     * @param formId ID of the form
     * @return {@link PublishedFormDetailsById} instance
     * @throws BadRequestException
     */
    public PublishedFormDetailsById getPublishedFormById(final String formId, final String userId,
                                                         final String role) throws BadRequestException {
        List<Form> form;
        if (role.equals(ROLE_GUEST)) {
            form =  formsRepository.findFormByFormAndUserId(formId, userId);
        } else {
            form = formsRepository.findById(formId).stream().toList();
        }
        CommonUtils.validateFormResponse(form, formId);
        return PublishedFormDetailsById.from(form.get(0));
    }

    /**
     * Method to delete form by formId
     *
     * @param formId ID of the form
     */
    public void deleteFormById(final String formId, final String userId) throws BadRequestException {
        final List<Form> form = formsRepository.findFormByFormAndUserId(formId, userId);
        CommonUtils.validateFormResponse(form, formId);
        formsRepository.deleteById(formId);
    }

    /**
     * Helper method to set section, question and option IDs if missing
     *
     * @param request {@link FormCreateUpdateRequest} instance
     */
    private void setIds(final FormCreateUpdateRequest request) {
        if (!CollectionUtils.isEmpty(request.getSections())) {
            request.getSections().forEach(section -> {
                if (StringUtils.isBlank(section.getId())) {
                    section.setId(UUID.randomUUID().toString());
                }
                if (!CollectionUtils.isEmpty(section.getQuestions())) {
                    section.getQuestions().forEach(question -> {
                        if (StringUtils.isBlank(question.getId())) {
                            question.setId(UUID.randomUUID().toString());
                        }
                        if (!CollectionUtils.isEmpty(question.getOptions())) {
                            question.getOptions().forEach(option -> {
                                if (StringUtils.isBlank(option.getId())) {
                                    option.setId(UUID.randomUUID().toString());
                                }
                            });
                        }
                    });
                }
            });
        }
    }
}
