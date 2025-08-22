package com.adrianojlt.temporaling.workflows;

import com.adrianojlt.temporaling.activities.SendEmailActivity;
import com.adrianojlt.temporaling.models.EmailDetails;
import io.temporal.activity.ActivityOptions;
import io.temporal.failure.CanceledFailure;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.CancellationScope;
import io.temporal.workflow.Workflow;

import java.time.Duration;

@WorkflowImpl(workers = "send-email-worker")
public class SendEmailWorkflowImpl implements SendEmailWorkflow {

    private final EmailDetails emailDetails = new EmailDetails();

    private final ActivityOptions options = ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(10))
                    .build();

    private final SendEmailActivity activities = Workflow.newActivityStub(SendEmailActivity.class, options);

    @Override
    public void run(String email) {

        int duration = 4;

        emailDetails.email = email;
        emailDetails.message = "Welcome to our Subscription Workflow!";
        emailDetails.subscribed = true;
        emailDetails.count = 0;

        while (emailDetails.subscribed) {

            emailDetails.count += 1;

            if (emailDetails.count > 1) {
                emailDetails.message = "Thank you for staying subscribed!";
            }

            try {
                activities.sendEmail(emailDetails);
                Workflow.sleep(Duration.ofSeconds(duration));
            }
            catch (CanceledFailure e) {

                emailDetails.subscribed = false;
                emailDetails.message = "Sorry to see you go";

                CancellationScope sendGoodbye = Workflow.newDetachedCancellationScope(() -> activities.sendEmail(emailDetails));
                sendGoodbye.run();

                return;
            }
        }
    }

    @Override
    public EmailDetails details() {
        return emailDetails;
    }
}
