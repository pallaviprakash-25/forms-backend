package com.project.forms.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublishedSection {
    private String id;
    private String name;
    private String description;
    private List<PublishedQuestion> questions;

    public static PublishedSection from(final Section section) {
        final PublishedSection publishedSection = new PublishedSection();
        BeanUtils.copyProperties(section, publishedSection);
        if (!CollectionUtils.isEmpty(section.getQuestions())) {
            final List<PublishedQuestion> publishedQuestions = section.getQuestions().stream()
                    .map(PublishedQuestion::from)
                    .collect(Collectors.collectingAndThen(Collectors.toList(), collected -> {
                        if (section.isShuffle()) {
                            Collections.shuffle(collected);
                        }
                        return collected;
                    }));
            publishedSection.setQuestions(publishedQuestions);
        }
        return publishedSection;
    }

}
