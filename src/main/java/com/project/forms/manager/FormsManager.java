package com.project.forms.manager;

import com.project.forms.dao.model.Form;
import com.project.forms.dao.request.FormCreateUpdateRequest;
import com.project.forms.dao.response.FormCreateUpdateResponse;
import com.project.forms.dao.response.FormDetailsById;
import com.project.forms.dao.response.FormDetailsByUserId;
import com.project.forms.dao.response.PublishedFormDetailsById;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public FormCreateUpdateResponse createUpdateForm(final FormCreateUpdateRequest request) throws BadRequestException {
        final Date currentDate = Date.from(Instant.now());
        if (StringUtils.isAllEmpty(request.getFormId())) {
            // create request
            final String formId = UUID.randomUUID().toString();
            request.setFormId(formId);
            request.getAudit().setCreatedOn(currentDate);
            request.getAudit().setModifiedOn(currentDate);

        } else {
            // update request
            final Optional<Form> form = formsRepository.findById(request.getFormId());
            if (form.isEmpty()) {
                log.error("Form with ID {} does not exist", request.getFormId());
                throw new BadRequestException("Form ID does not exist");
            }
            request.getAudit().setModifiedOn(currentDate);
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
    public FormDetailsById getFormByFormId(final String formId) throws BadRequestException {
        final Optional<Form> form = formsRepository.findById(formId);
        if (form.isEmpty()) {
            log.error("Form with ID {} does not exist", formId);
            throw new BadRequestException("Form ID does not exist");
        }
        return FormDetailsById.from(form.get());
    }

    /**
     * Method to fetch details of all forms created by a user
     *
     * @param userId ID of the user whose forms need to be fetched
     * @return {@link FormDetailsByUserId} instance
     */
    public FormDetailsByUserId getFormByUserId(final String userId) {
        final List<Form> forms = formsRepository.findAllByUserId(userId);
        return FormDetailsByUserId.from(forms);
    }

    /**
     * Method to fetch published form details based on formId
     *
     * @param formId ID of the form
     * @return {@link PublishedFormDetailsById} instance
     * @throws BadRequestException
     */
    public PublishedFormDetailsById getPublishedFormById(final String formId) throws BadRequestException {
        final Optional<Form> form = formsRepository.findById(formId);
        if (form.isEmpty()) {
            log.error("Form with ID {} does not exist", formId);
            throw new BadRequestException("Form ID does not exist");
        }
        return PublishedFormDetailsById.from(form.get());
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
