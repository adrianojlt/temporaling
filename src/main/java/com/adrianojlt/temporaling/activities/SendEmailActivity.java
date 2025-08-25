package com.adrianojlt.temporaling.activities;

import com.adrianojlt.temporaling.models.EmailDetails;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface SendEmailActivity {
    @ActivityMethod
    public String sendEmail(EmailDetails details);
}
