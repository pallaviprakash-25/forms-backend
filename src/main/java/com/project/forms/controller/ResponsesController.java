package com.project.forms.controller;

import com.project.forms.dao.request.FormResponseSaveRequest;
import com.project.forms.manager.ResponsesManager;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.project.forms.utils.APIConstants.FORM_RESPONSE_PATH;

@Controller
@RequestMapping(value = FORM_RESPONSE_PATH)
public class ResponsesController {

    @Autowired
    private ResponsesManager responsesManager;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> saveFormResponse(@Valid @RequestBody final FormResponseSaveRequest request) throws BadRequestException {
        responsesManager.saveFormResponse(request);
        return ResponseEntity.ok().build();
    }
}
