package com.adrianojlt.temporaling.activities;

import com.adrianojlt.temporaling.models.EmailDetails;
import io.temporal.spring.boot.ActivityImpl;
import java.text.MessageFormat;

@ActivityImpl(workers = "send-email-worker")
public class SendEmailActivityImpl implements SendEmailActivity {

    @Override
    public String sendEmail(EmailDetails details) {

        return MessageFormat.format( "Sending email to {0} with message: {1}, count: {2}",
                details.email,
                details.message,
                details.count);
    }
}
