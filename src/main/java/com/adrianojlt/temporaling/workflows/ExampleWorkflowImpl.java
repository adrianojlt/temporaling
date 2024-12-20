package com.adrianojlt.temporaling.workflows;

import com.adrianojlt.temporaling.activities.ExampleActivity;
import io.temporal.activity.ActivityOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;

import java.time.Duration;

@WorkflowImpl(workers = "hello-worker")
public class ExampleWorkflowImpl implements ExampleWorkflow {

    private final ActivityOptions options =
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(10))
                    .build();

    private final ExampleActivity exampleActivity = Workflow.newActivityStub(ExampleActivity.class, options);

    @Override
    public void execute() {
        exampleActivity.hello();
    }
}
