package com.adrianojlt.temporaling.activities;

import com.adrianojlt.temporaling.models.EmailDetails;
import io.temporal.spring.boot.ActivityImpl;
import lombok.extern.log4j.Log4j2;

import java.text.MessageFormat;

@Log4j2
@ActivityImpl(workers = "send-email-worker")
public class SendEmailActivityImpl implements SendEmailActivity {

    @Override
    public void sendEmail(EmailDetails details) {

        String response = MessageFormat.format( "Sending email to {0} with message: {1}, count: {2}",
                details.email,
                details.message,
                details.count);

        log.info(response);
    }
}
