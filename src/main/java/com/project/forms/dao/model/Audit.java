package com.project.forms.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Audit {
    private String createdBy;
    private Date createdOn;
    private String modifiedBy;
    private Date modifiedOn;
}
