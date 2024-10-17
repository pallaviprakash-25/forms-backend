package com.project.forms.repository;

import com.project.forms.dao.model.Response;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsesRepository extends MongoRepository<Response, String> {
}
