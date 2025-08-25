package com.adrianojlt.temporaling.services;

import com.adrianojlt.temporaling.enums.TaskQueues;
import com.adrianojlt.temporaling.models.EmailDetails;
import com.adrianojlt.temporaling.workflows.SendEmailWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final WorkflowClient client;

    public EmailService(WorkflowClient client) {
        this.client = client;
    }

    public void startWorkflow(String email, Integer duration) {

        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue(TaskQueues.EMAIL_WORKFLOW_TASK_QUEUE.name())
                .setWorkflowId(email)
                .build();

        SendEmailWorkflow workflow = client.newWorkflowStub(SendEmailWorkflow.class, options);
        WorkflowClient.start(workflow::run, email, duration);
    }

    public EmailDetails getWorkflowDetails(String email) {
        SendEmailWorkflow workflow = client.newWorkflowStub(SendEmailWorkflow.class, email);
        return workflow.details();
    }

    public void cancelWorkflow(String email) {
        SendEmailWorkflow workflow = client.newWorkflowStub(SendEmailWorkflow.class, email);
        WorkflowStub workflowStub = WorkflowStub.fromTyped(workflow);
        workflowStub.cancel();
    }
}
