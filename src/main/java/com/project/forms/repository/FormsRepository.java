package com.project.forms.repository;

import com.project.forms.dao.model.Form;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormsRepository extends MongoRepository<Form, String> {

    @Query("{ 'audit.createdBy' : ?0 }")
    List<Form> findAllFormsByUserId(final String userId);

    @Query("{ 'formId': ?0, 'audit.createdBy': ?1 }")
    List<Form> findFormByFormAndUserId(final String formId, final String userId);

    @Query("{ 'formId': ?0, 'audit.createdBy': { $ne: ?1 } }")
    List<Form> findFormByFormIdNotCreatedByUserId(final String formId, final String userId);
}
