package com.project.forms.dao.model;

import lombok.Data;

import java.util.Date;

@Data
public class Audit {
    private String createdBy;
    private Date createdOn;
    private String modifiedBy;
    private Date modifiedOn;
}
