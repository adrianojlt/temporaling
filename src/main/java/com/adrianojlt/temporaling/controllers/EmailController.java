package com.adrianojlt.temporaling.controllers;

import com.adrianojlt.temporaling.models.EmailDetails;
import com.adrianojlt.temporaling.services.EmailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/workflows/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping(value = "/subscribe", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> subscribe(@RequestParam String email) {

        this.emailService.startWorkflow(email);
        return ResponseEntity.ok("subscribe created successfully");
    }

    @GetMapping(value = "/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmailDetails getDetails(@RequestParam String email) {
        return this.emailService.getWorkflowDetails(email);
    }

    @DeleteMapping(value = "/unsubscribe", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> endSubscription(@RequestParam String email) {
        this.emailService.cancelWorkflow(email);
        return ResponseEntity.ok("unsubscribed!");
    }
}
