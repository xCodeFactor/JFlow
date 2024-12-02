package com.jflow.codeexecution.controller;

import com.jflow.codeexecution.model.CodeSnippet;
import com.jflow.codeexecution.service.CodeExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/code-execution")
public class CodeExecutionController {
    @Autowired
    private CodeExecutionService codeExecutionService;

    @PostMapping("/execute")
    public CodeSnippet executeCode(@RequestBody CodeSnippet codeSnippet) {
        return codeExecutionService.executeCode(codeSnippet);
    }
}