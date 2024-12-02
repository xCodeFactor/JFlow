package com.jflow.Collaboration.controller;

import com.jflow.Collaboration.model.CodeChange;
import com.jflow.Collaboration.service.CollaborationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/collaboration")
public class CollaborationController {
    @Autowired
    private CollaborationService collaborationService;

    @PostMapping("/code-change")
    public void handleCodeChange(@RequestBody CodeChange codeChange) {
        collaborationService.saveCodeChange(codeChange);
    }
}
