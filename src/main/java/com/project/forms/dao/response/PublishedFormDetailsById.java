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
public class PublishedFormDetailsById {
    private List<PublishedSection> sections;

    public static PublishedFormDetailsById from(final Form form) {
        final PublishedFormDetailsById publishedFormDetails = new PublishedFormDetailsById();
        if (!CollectionUtils.isEmpty(form.getSections())) {
            final List<PublishedSection> publishedSections = form.getSections().stream()
                    .map(PublishedSection::from)
                    .toList();
            publishedFormDetails.setSections(publishedSections);
        }
        return publishedFormDetails;
    }
}
