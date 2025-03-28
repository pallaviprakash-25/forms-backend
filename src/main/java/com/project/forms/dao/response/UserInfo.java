package com.project.forms.dao.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo {
    private String sub;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String email;
    @JsonIgnore
    @JsonProperty
    private boolean email_verified;
    private String locale;

    public UserInfo(final String name, final String email, final String picture) {
        this.name=name;
        this.email=email;
        this.picture=picture;
    }
}
