package com.jflow.codeexecution.repository;


import com.jflow.codeexecution.model.CodeSnippet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CodeSnippetRepository extends MongoRepository<CodeSnippet, String> {
}
