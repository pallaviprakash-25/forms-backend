package com.project.forms.controller;

import com.project.forms.dao.request.FormCreateUpdateRequest;
import com.project.forms.dao.response.FormCreateUpdateResponse;
import com.project.forms.dao.response.FormDetailsById;
import com.project.forms.dao.response.FormDetailsByUserId;
import com.project.forms.dao.response.PublishedFormDetailsById;
import com.project.forms.manager.FormsManager;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import static com.project.forms.utils.APIConstants.*;
import static com.project.forms.utils.CommonUtils.getUserId;

@RestController
@RequestMapping(value = FORMS_PATH)
public class FormsController {

    @Autowired
    private FormsManager formsService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormCreateUpdateResponse> createOrUpdateForm(@Valid @RequestBody final FormCreateUpdateRequest request,
                                                                       @AuthenticationPrincipal final OAuth2User user) throws BadRequestException {
        final FormCreateUpdateResponse response = formsService.createUpdateForm(request, getUserId(user));
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormDetailsById> fetchFormByFormId(@PathVariable final String id,
                                                             @AuthenticationPrincipal final OAuth2User user) throws BadRequestException {
        final FormDetailsById response = formsService.getFormByFormAndUserId(id, getUserId(user));
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = USER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormDetailsByUserId> fetchFormByUserId(@AuthenticationPrincipal final OAuth2User user) {
        final FormDetailsByUserId response = formsService.getFormByUserId(getUserId(user));
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = PUBLISHED_FORM_BY_ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PublishedFormDetailsById> fetchPublishedFormById(@PathVariable final String id,
                                                                           @AuthenticationPrincipal final OAuth2User user) throws BadRequestException {
        final PublishedFormDetailsById response = formsService.getPublishedFormById(id, getUserId(user));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = ID)
    public ResponseEntity<Void> deleteFormById(@PathVariable final String id,
                                               @AuthenticationPrincipal final OAuth2User user) throws BadRequestException {
        formsService.deleteFormById(id, getUserId(user));
        return ResponseEntity.ok().build();
    }
}
