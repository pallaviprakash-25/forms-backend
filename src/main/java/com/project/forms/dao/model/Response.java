package com.project.forms.dao.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

import static com.project.forms.utils.Constants.RESPONSE;

@Document(collection = RESPONSE)
@Data
public class Response {
    @Id
    private String responseId;

    private String formId;

    private String submittedBy;

    private Date submittedOn;

    private List<ResponseSection> sections;
}
