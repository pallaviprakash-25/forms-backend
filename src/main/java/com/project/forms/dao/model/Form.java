package com.project.forms.dao.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "form")
@Data
public class Form {
    @Id
    private String formId;
    private String formName;
    private List<Section> sections;
}
