package com.adrianojlt.temporaling.controllers;

import com.adrianojlt.temporaling.models.ErrorResponse;
import com.adrianojlt.temporaling.services.EmailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> subscribe(@RequestParam String email, @RequestParam Integer duration) {
        this.emailService.startWorkflow(email, duration);
        return ResponseEntity.ok("subscribe created successfully");
    }

    @GetMapping(value = "/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDetails(@RequestParam String email) {

        var result = this.emailService.getWorkflowDetails(email);

        if (result == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(this.emailService.getWorkflowDetails(email));
    }

    @DeleteMapping(value = "/unsubscribe", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> endSubscription(@RequestParam String email) {
        this.emailService.cancelWorkflow(email);
        return ResponseEntity.ok("unsubscribed!");
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleExceptions(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
