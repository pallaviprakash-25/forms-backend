package com.project.forms.dao.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetails {
    private String id;
    private String name;
    private String email;
    private String picture;
}
