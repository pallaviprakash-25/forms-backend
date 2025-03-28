package com.project.forms.manager;

import com.project.forms.dao.model.*;
import com.project.forms.dao.request.FormResponseSaveRequest;
import com.project.forms.dao.response.ResponsesByFormId;
import com.project.forms.repository.FormsRepository;
import com.project.forms.repository.ResponsesRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.*;

import static com.project.forms.utils.Constants.MOCK_USER;

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
    public void saveFormResponse(final FormResponseSaveRequest request, final String userId) throws BadRequestException {
        List<Form> form;
        if (userId.equals(MOCK_USER)) {
            form = formsRepository.findFormByFormAndUserId(request.getFormId(), userId);
        } else {
            form = formsRepository.findById(request.getFormId()).stream().toList();
        }
        if (form.isEmpty()) {
            log.error("Form with ID {} does not exist", request.getFormId());
            throw new BadRequestException("Form ID does not exist");
        }

        validateSectionIds(form.get(0), request);

        final Response response = new Response();
        BeanUtils.copyProperties(request, response);

        final String responseId = UUID.randomUUID().toString();
        response.setResponseId(responseId);

        final Date currentDate = Date.from(Instant.now());
        response.setSubmittedOn(currentDate);

        responsesRepository.save(response);
    }

    /**
     * Method to fetch all responses for a form
     *
     * @param formId ID of the form
     * @return {@link ResponsesByFormId} instance containing list of responses
     * @throws BadRequestException
     */
    public ResponsesByFormId getAllResponses(final String formId, final String userId) throws BadRequestException {
        final List<Form> form = formsRepository.findFormByFormAndUserId(formId, userId);
        if (form.isEmpty()) {
            log.error("Form with ID {} does not exist", formId);
            throw new BadRequestException("Form ID does not exist");
        }
        final List<Response> responses = responsesRepository.findAllByFormId(formId);
        return new ResponsesByFormId(responses);
    }

    /**
     * Helper method to validate section IDs
     *
     * @param form         form details
     * @param formResponse form response to be saved
     * @throws BadRequestException
     */
    private void validateSectionIds(final Form form, final FormResponseSaveRequest formResponse) throws BadRequestException {
        if (!CollectionUtils.isEmpty(formResponse.getSections())) {
            final List<String> existingSectionIds = form.getSections().stream()
                    .map(Section::getId)
                    .toList();
            for (ResponseSection responseSection : formResponse.getSections()) {
                if (!existingSectionIds.contains(responseSection.getId())) {
                    final String errorMsg = String.format("Section ID %s does not exist", responseSection.getId());
                    log.error(errorMsg);
                    throw new BadRequestException(errorMsg);
                }

                final Section existingSection = form.getSections().stream()
                        .filter(sec -> sec.getId().equals(responseSection.getId()))
                        .findFirst()
                        .orElse(null);
                validateQuestionIds(existingSection, responseSection);
            }
        }
        checkRequiredQuestions(form, formResponse);
    }

    /**
     * Helper method to validate question IDs in a section
     *
     * @param existingSection existing section in the form
     * @param responseSection section in the form response
     * @throws BadRequestException
     */
    private void validateQuestionIds(final Section existingSection,
                                     final ResponseSection responseSection) throws BadRequestException {
        if (!CollectionUtils.isEmpty(responseSection.getQuestions())) {
            final List<String> existingQuestionIds = existingSection.getQuestions().stream()
                    .map(Question::getId)
                    .toList();
            for (ResponseQuestion responseQuestion : responseSection.getQuestions()) {
                if (!existingQuestionIds.contains(responseQuestion.getId())) {
                    final String errorMsg = String.format("Question ID %s does not exist", responseQuestion.getId());
                    log.error(errorMsg);
                    throw new BadRequestException(errorMsg);
                }

                final Question existingQuestion = existingSection.getQuestions().stream()
                        .filter(ques -> ques.getId().equals(responseQuestion.getId()))
                        .findFirst()
                        .orElse(null);
                validateOptionIds(existingQuestion, responseQuestion);
            }
        }
    }

    /**
     * Helper method to validate option IDs in a question in the form
     *
     * @param existingQuestion existing question in a section of the form
     * @param responseQuestion question in the form response
     * @throws BadRequestException
     */
    private void validateOptionIds(final Question existingQuestion,
                                   final ResponseQuestion responseQuestion) throws BadRequestException {
        final List<String> existingOptionIds = existingQuestion.getOptions().stream()
                .map(Option::getId)
                .toList();
        for (String responseOptionId : responseQuestion.getOptions()) {
            if (!existingOptionIds.contains(responseOptionId)) {
                final String errorMsg = String.format("Option ID %s does not exist", responseOptionId);
                log.error(errorMsg);
                throw new BadRequestException(errorMsg);
            }
        }
    }

    /**
     * Helper method to check whether a required question is answered in the response
     *
     * @param form         form details
     * @param formResponse form response to be saved
     * @throws BadRequestException
     */
    private void checkRequiredQuestions(final Form form,
                                        final FormResponseSaveRequest formResponse) throws BadRequestException {
        for (Section existingSection : form.getSections()) {
            final List<String> responseQuestionIds = Optional.ofNullable(formResponse.getSections())
                    .orElse(Collections.emptyList()) // Handle null sections
                    .stream()
                    .filter(sec -> sec.getId().equals(existingSection.getId()))
                    .findFirst()
                    .map(ResponseSection::getQuestions)
                    .orElse(Collections.emptyList()) // Handle null or empty questions
                    .stream()
                    .map(ResponseQuestion::getId)
                    .toList();
            for (Question existingQuestion : existingSection.getQuestions()) {
                if (existingQuestion.isRequired() && !responseQuestionIds.contains(existingQuestion.getId())) {
                    final String errorMsg = String.format("Question ID %s is required", existingQuestion.getId());
                    log.error(errorMsg);
                    throw new BadRequestException(errorMsg);
                }
            }
        }
    }
}
