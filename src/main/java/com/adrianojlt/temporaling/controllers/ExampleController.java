package com.adrianojlt.temporaling.controllers;

import com.adrianojlt.temporaling.services.ExampleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/workflows/examples")
public class ExampleController {

    private final ExampleService exampleService;

    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @PostMapping("/hello")
    public ResponseEntity<?> hello() {
        exampleService.startExampleWorkflow();
        return ResponseEntity.ok().build();
    }
}
