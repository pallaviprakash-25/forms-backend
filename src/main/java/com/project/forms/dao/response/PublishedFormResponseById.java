package com.project.forms.dao.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.forms.dao.model.Form;
import com.project.forms.dao.model.PublishedSection;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublishedFormResponseById {
    private String formId;
    private List<PublishedSection> sections;

    public static PublishedFormResponseById from(final Form form) {
        final PublishedFormResponseById publishedFormResponse = new PublishedFormResponseById();
        publishedFormResponse.setFormId(form.getFormId());
        if (!CollectionUtils.isEmpty(form.getSections())) {
            final List<PublishedSection> publishedSections = form.getSections().stream()
                    .map(PublishedSection::from)
                    .toList();
            publishedFormResponse.setSections(publishedSections);
        }
        return publishedFormResponse;
    }
}
