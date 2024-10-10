package com.project.forms.controller;

import com.project.forms.dao.request.FormCreateUpdateRequest;
import com.project.forms.dao.response.FormCreateUpdateResponse;
import com.project.forms.service.FormsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/v1/forms")
public class FormsController {

    @Autowired
    private FormsService formsService;

    @PostMapping
    public ResponseEntity<FormCreateUpdateResponse> createOrUpdateForm(@RequestBody final FormCreateUpdateRequest request) {
        final FormCreateUpdateResponse response = formsService.createUpdateForm(request);
        return ResponseEntity.ok(response);
    }
}
