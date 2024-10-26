package com.project.forms.dao.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import static com.project.forms.utils.Constants.FORM;

@Document(collection = FORM)
@Data
public class Form {
    @Id
    private String formId;
    private String formName;
    private List<Section> sections;
    private Audit audit;
}
