package com.project.forms.controller;

import com.project.forms.dao.request.FormResponseSaveRequest;
import com.project.forms.dao.response.ResponsesByFormId;
import com.project.forms.manager.ResponsesManager;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import static com.project.forms.utils.APIConstants.FORM_RESPONSE_PATH;
import static com.project.forms.utils.APIConstants.ID;
import static com.project.forms.utils.CommonUtils.getUserId;

@RestController
@RequestMapping(value = FORM_RESPONSE_PATH)
public class ResponsesController {

    @Autowired
    private ResponsesManager responsesManager;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveFormResponse(@Valid @RequestBody final FormResponseSaveRequest request,
                                                 @AuthenticationPrincipal final OAuth2User user) throws BadRequestException {
        responsesManager.saveFormResponse(request, getUserId(user));
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsesByFormId> getAllResponses(@PathVariable final String id,
                                                             @AuthenticationPrincipal final OAuth2User user) throws BadRequestException {
        final ResponsesByFormId response = responsesManager.getAllResponses(id, getUserId(user));
        return ResponseEntity.ok(response);
    }
}
