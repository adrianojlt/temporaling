package com.adrianojlt.temporaling.workflows;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface ExampleWorkflow {

    @WorkflowMethod
    public void hello();
}
