package com.jflow.codeexecution.service;

import com.jflow.codeexecution.model.CodeSnippet;
import com.jflow.codeexecution.repository.CodeSnippetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class CodeExecutionService {
    @Autowired
    private CodeSnippetRepository codeSnippetRepository;

    @Autowired
    private KafkaTemplate<String, CodeSnippet> kafkaTemplate;

    public CodeSnippet executeCode(CodeSnippet codeSnippet) {
        try {
            // Create a temporary directory for the Java file
            File tempDir = Files.createTempDirectory("java-code").toFile();
            File javaFile = new File(tempDir, "Main.java");

            // Write the code to the Java file
            try (FileWriter writer = new FileWriter(javaFile)) {
                writer.write(codeSnippet.getCode());
            }

            // Compile the Java file
            ProcessBuilder compileProcessBuilder = new ProcessBuilder("javac", javaFile.getAbsolutePath());
            compileProcessBuilder.directory(tempDir);
            Process compileProcess = compileProcessBuilder.start();
            compileProcess.waitFor();

            // Check for compilation errors
            if (compileProcess.exitValue() != 0) {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()));
                StringBuilder errorOutput = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorOutput.append(line).append("\n");
                }
                codeSnippet.setOutput("Compilation Error:\n" + errorOutput.toString());
                return codeSnippet;
            }

            // Execute the compiled Java class
            ProcessBuilder executeProcessBuilder = new ProcessBuilder("java", "-cp", tempDir.getAbsolutePath(), "Main");
            executeProcessBuilder.directory(tempDir);
            Process executeProcess = executeProcessBuilder.start();

            BufferedReader outputReader = new BufferedReader(new InputStreamReader(executeProcess.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = outputReader.readLine()) != null) {
                output.append(line).append("\n");
            }

            codeSnippet.setOutput(output.toString());
            codeSnippetRepository.save(codeSnippet);
            kafkaTemplate.send("code-execution-results", codeSnippet);
        } catch (Exception e) {
            codeSnippet.setOutput("Error: " + e.getMessage());
        }

        return codeSnippet;
    }
}