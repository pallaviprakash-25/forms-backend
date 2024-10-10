package com.project.forms.dao.model;

import lombok.Data;

import java.util.List;

@Data
public class Section {
    private String sectionId;
    private String name;
    private String description;
    private boolean shuffle;
    private List<Question> questions;

}
