package com.project.forms.controller;

import com.project.forms.dao.request.FormCreateUpdateRequest;
import com.project.forms.dao.response.FormCreateUpdateResponse;
import com.project.forms.dao.response.FormResponseById;
import com.project.forms.dao.response.FormResponseByUserId;
import com.project.forms.dao.response.PublishedFormResponseById;
import com.project.forms.manager.FormsManager;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.project.forms.utils.APIConstants.*;

@Controller
@RequestMapping(value = FORMS_PATH)
public class FormsController {

    @Autowired
    private FormsManager formsService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<FormCreateUpdateResponse> createOrUpdateForm(@Valid @RequestBody final FormCreateUpdateRequest request) {
        final FormCreateUpdateResponse response = formsService.createUpdateForm(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = ID, produces = "application/json")
    public ResponseEntity<FormResponseById> fetchFormByFormId(@PathVariable final String id) throws BadRequestException {
        final FormResponseById response = formsService.getFormByFormId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = USER_ID_PATH, produces = "application/json")
    public ResponseEntity<FormResponseByUserId> fetchFormByUserId(@PathVariable final String id) {
        final FormResponseByUserId response = formsService.getFormByUserId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = PUBLISHED_FORM_BY_ID_PATH, produces = "application/json")
    public ResponseEntity<PublishedFormResponseById> fetchPublishedFormById(@PathVariable final String id) throws BadRequestException {
        final PublishedFormResponseById response = formsService.getPublishedFormById(id);
        return ResponseEntity.ok(response);
    }
}
