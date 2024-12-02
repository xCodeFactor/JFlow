
package com.jflow.Collaboration.repository;

import com.jflow.Collaboration.model.CodeChange;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CodeChangeRepository extends MongoRepository<CodeChange, String> {
}