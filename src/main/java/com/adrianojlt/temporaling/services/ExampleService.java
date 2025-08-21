package com.adrianojlt.temporaling.services;

import com.adrianojlt.temporaling.enums.TaskQueues;
import com.adrianojlt.temporaling.workflows.ExampleWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.common.RetryOptions;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ExampleService {

    private final WorkflowClient workflowClient;

    public ExampleService(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    public void startExampleWorkflow() {

        var workflow = workflowClient.newWorkflowStub(
                ExampleWorkflow.class,
                WorkflowOptions.newBuilder()
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(1).build())
                    .setTaskQueue(TaskQueues.EXAMPLE_WORKFLOW_TASK_QUEUE.name())
                    .setWorkflowId("example-workflow-id")
                    .build());

        io.temporal.client.WorkflowClient.start(workflow::hello);
    }
}
