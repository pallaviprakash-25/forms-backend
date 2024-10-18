package com.project.forms.repository;

import com.project.forms.dao.model.Response;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponsesRepository extends MongoRepository<Response, String> {

    @Query("{ 'formId' : ?0 }")
    List<Response> findAllByFormId(final String formId);
}
