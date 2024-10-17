package com.project.forms.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublishedQuestion {
    private String id;
    private String label;
    private String type;
    private boolean required;
    private List<Option> options;

    public static PublishedQuestion from(final Question question) {
        final PublishedQuestion publishedQuestion = new PublishedQuestion();
        if (question.isShuffle()) {
            Collections.shuffle(question.getOptions());
        }
        BeanUtils.copyProperties(question, publishedQuestion);
        return publishedQuestion;
    }
}
