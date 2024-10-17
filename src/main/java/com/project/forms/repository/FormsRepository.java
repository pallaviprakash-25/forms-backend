package com.project.forms.repository;

import com.project.forms.dao.model.Form;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormsRepository extends MongoRepository<Form, String> {

    @Query("{ 'audit.createdBy' : ?0 }")
    List<Form> findAllByUserId(final String userId);
}
