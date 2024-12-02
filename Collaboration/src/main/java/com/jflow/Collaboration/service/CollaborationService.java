package com.jflow.Collaboration.service;

import com.jflow.Collaboration.model.CodeChange;
import com.jflow.Collaboration.repository.CodeChangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CollaborationService {
    @Autowired
    private CodeChangeRepository codeChangeRepository;

    @Autowired
    private KafkaTemplate<String, CodeChange> kafkaTemplate;

    public void saveCodeChange(CodeChange codeChange) {
        codeChangeRepository.save(codeChange);
        kafkaTemplate.send("code-changes", codeChange);
    }
}
