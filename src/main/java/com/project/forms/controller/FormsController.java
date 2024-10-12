package com.project.forms.controller;

import com.project.forms.dao.request.FormCreateUpdateRequest;
import com.project.forms.dao.response.FormCreateUpdateResponse;
import com.project.forms.manager.FormsManager;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.project.forms.utils.APIConstants.FORMS_PATH;

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
}
