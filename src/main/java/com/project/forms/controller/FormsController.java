package com.project.forms.controller;

import com.project.forms.dao.request.FormCreateUpdateRequest;
import com.project.forms.dao.response.FormCreateUpdateResponse;
import com.project.forms.dao.response.FormResponse;
import com.project.forms.manager.FormsManager;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.project.forms.utils.APIConstants.FORMS_PATH;
import static com.project.forms.utils.APIConstants.ID;

@Controller
@RequestMapping(value = FORMS_PATH)
public class FormsController {

    @Autowired
    private FormsManager formsService;

    @PostMapping
    public ResponseEntity<FormCreateUpdateResponse> createOrUpdateForm(@Valid @RequestBody final FormCreateUpdateRequest request) {
        final FormCreateUpdateResponse response = formsService.createUpdateForm(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping(ID)
    public ResponseEntity<FormResponse> fetchFormByFormId(@PathVariable final String id) throws BadRequestException {
        final FormResponse response = formsService.getFormById(id);
        return ResponseEntity.ok(response);
    }
}
