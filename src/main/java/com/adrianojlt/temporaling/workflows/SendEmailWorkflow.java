package com.adrianojlt.temporaling.workflows;

import com.adrianojlt.temporaling.models.EmailDetails;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface SendEmailWorkflow {

    @WorkflowMethod
    public void run(String email, Integer duration);

    @QueryMethod
    public EmailDetails details();
}
