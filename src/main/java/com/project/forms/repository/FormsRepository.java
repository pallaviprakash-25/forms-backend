package com.project.forms.repository;

import com.project.forms.dao.model.Form;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormsRepository extends MongoRepository<Form, String> {
}
