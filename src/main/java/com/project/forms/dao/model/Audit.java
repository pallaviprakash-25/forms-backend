package com.project.forms.dao.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Audit {
    private String createdBy;
    private Date createdOn;
    private String modifiedBy;
    private Date modifiedOn;
}
