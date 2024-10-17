package com.project.forms.manager;

import com.project.forms.dao.model.*;
import com.project.forms.dao.request.FormResponseSaveRequest;
import com.project.forms.repository.FormsRepository;
import com.project.forms.repository.ResponsesRepository;
import lombok.extern.slf4j.Slf4j;
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
public class ResponsesManager {

    @Autowired
    private FormsRepository formsRepository;

    @Autowired
    private ResponsesRepository responsesRepository;

    /**
     * Method to save form response
     *
     * @param request {@link FormResponseSaveRequest} instance
     * @throws BadRequestException
     */
    public void saveFormResponse(final FormResponseSaveRequest request) throws BadRequestException {
        final Optional<Form> form = formsRepository.findById(request.getFormId());
        if (form.isEmpty()) {
            log.error("Form with ID {} does not exist", request.getFormId());
            throw new BadRequestException("Form ID does not exist");
        }

        validateIds(form.get(), request);

        final Response response = new Response();
        BeanUtils.copyProperties(request, response);

        final String responseId = UUID.randomUUID().toString();
        response.setResponseId(responseId);

        final Date currentDate = Date.from(Instant.now());
        response.setSubmittedOn(currentDate);

        responsesRepository.save(response);
    }

    /**
     * Helper method to validate if section, question and option IDs in the request exist in the form
     *
     * @param form    existing form
     * @param request form response to be saved
     * @throws BadRequestException
     */
    private void validateIds(final Form form, final FormResponseSaveRequest request) throws BadRequestException{
        if (!CollectionUtils.isEmpty(request.getSections())) {
            validateSectionIds(form, request);
        }
    }

    /**
     * Helper method to validate section IDs
     *
     * @param form    existing form
     * @param request form response to be saved
     * @throws BadRequestException
     */
    private static void validateSectionIds(final Form form, FormResponseSaveRequest request) throws BadRequestException {
        final List<String> existingSectionIds = form.getSections().stream()
                .map(Section::getId)
                .toList();
        for (ResponseSection responseSection : request.getSections()) {
            if (!existingSectionIds.contains(responseSection.getId())) {
                final String errorMsg = String.format("Section ID %s does not exist in form %s",
                        responseSection.getId(), form.getFormId());
                log.error(errorMsg);
                throw new BadRequestException(errorMsg);
            }
            final Section existingSection = form.getSections().stream()
                    .filter(sec -> sec.getId().equals(responseSection.getId()))
                    .findFirst()
                    .orElse(null);
            if (!CollectionUtils.isEmpty(responseSection.getQuestions())) {
                validateQuestionIds(form.getFormId(), existingSection, responseSection);
            }
        }
    }

    /**
     * Helper method to validate question IDs in a section
     *
     * @param formId          ID of the form
     * @param existingSection existing section in the form
     * @param responseSection section in the form response
     * @throws BadRequestException
     */
    private static void validateQuestionIds(final String formId, final Section existingSection,
                                            final ResponseSection responseSection) throws BadRequestException {
        final List<String> existingQuestionIds = existingSection.getQuestions().stream()
                .map(Question::getId)
                .toList();
        for (ResponseQuestion responseQuestion: responseSection.getQuestions()) {
            if (!existingQuestionIds.contains(responseQuestion.getId())) {
                final String errorMsg = String.format("Question ID %s does not exist in section %s within form %s",
                        responseQuestion.getId(), existingSection.getId(), formId);
                log.error(errorMsg);
                throw new BadRequestException(errorMsg);
            }
            final Question existingQuestion = existingSection.getQuestions().stream()
                    .filter(ques -> ques.getId().equals(responseQuestion.getId()))
                    .findFirst()
                    .orElse(null);
            validateOptions(formId, existingSection.getId(), existingQuestion, responseQuestion);
        }
    }

    /**
     * Helper method to validate option IDs in a question in the form
     *
     * @param formId           ID of the form
     * @param sectionId        ID of the section
     * @param existingQuestion existing question in a section of the form
     * @param responseQuestion question in the form response
     * @throws BadRequestException
     */
    private static void validateOptions(final String formId, final String sectionId,
                                        final Question existingQuestion,
                                        final ResponseQuestion responseQuestion) throws BadRequestException {
        final List<String> existingOptionIds = existingQuestion.getOptions().stream()
                .map(Option::getId)
                .toList();
        for (String responseOptionId : responseQuestion.getOptions()) {
            if (!existingOptionIds.contains(responseOptionId)) {
                final String errorMsg = String.format("Option ID %s does not exist in question %s in" +
                        " section %s within form %s", responseOptionId, responseQuestion.getId(), sectionId,
                        formId);
                log.error(errorMsg);
                throw new BadRequestException(errorMsg);
            }
        }
    }
}
