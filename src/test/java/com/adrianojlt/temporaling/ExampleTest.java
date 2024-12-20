package com.adrianojlt.temporaling;

import com.adrianojlt.temporaling.activities.ExampleActivityImpl;
import com.adrianojlt.temporaling.workflows.ExampleWorkflow;
import com.adrianojlt.temporaling.workflows.ExampleWorkflowImpl;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.api.enums.v1.WorkflowExecutionStatus;
import io.temporal.api.workflowservice.v1.DescribeWorkflowExecutionRequest;
import io.temporal.api.workflowservice.v1.DescribeWorkflowExecutionResponse;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.testing.TestWorkflowEnvironment;
import io.temporal.testing.TestWorkflowExtension;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExampleTest {

    @RegisterExtension
    public static final TestWorkflowExtension testWorkflowExtension =
            TestWorkflowExtension.newBuilder()
                    .setWorkflowTypes(ExampleWorkflowImpl.class)
                    .setDoNotStart(true)
                    .build();

    @Test
    public void testExample() {

        try (TestWorkflowEnvironment testEnv = TestWorkflowEnvironment.newInstance()) {

            // Create a Worker for the task queue
            Worker worker = testEnv.newWorker("example-task-queue");
            worker.registerWorkflowImplementationTypes(ExampleWorkflowImpl.class);
            worker.registerActivitiesImplementations(new ExampleActivityImpl());

            // Start the test environment
            testEnv.start();

            // Create a WorkflowClient
            WorkflowClient client = testEnv.getWorkflowClient();

            // Create a workflow stub
            ExampleWorkflow workflow = client.newWorkflowStub(
                    ExampleWorkflow.class,
                    WorkflowOptions.newBuilder()
                            .setTaskQueue("example-task-queue")
                            .build()
            );

            // Start the workflow execution
            WorkflowExecution execution = WorkflowClient.start(workflow::execute);

            // Describe the workflow execution
            DescribeWorkflowExecutionResponse response = client.getWorkflowServiceStubs().blockingStub().describeWorkflowExecution(
                    DescribeWorkflowExecutionRequest.newBuilder()
                            .setNamespace(testEnv.getNamespace())
                            .setExecution(execution)
                            .build()
            );

            WorkflowExecutionStatus status = response.getWorkflowExecutionInfo().getStatus();

            // Assert that the workflow is running
            assertEquals(WorkflowExecutionStatus.WORKFLOW_EXECUTION_STATUS_RUNNING, status);
        }
    }

    @Test
    public void testExampleWithRealServer() {

        // Connect to Temporal Server
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
        WorkflowClient client = WorkflowClient.newInstance(service);

        // Create Worker Factory and Worker
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker("example-task-queue");

        // Register workflow and activity implementations
        worker.registerWorkflowImplementationTypes(ExampleWorkflowImpl.class);
        worker.registerActivitiesImplementations(new ExampleActivityImpl());

        // Start the Worker
        factory.start();

        // Create a workflow stub
        ExampleWorkflow workflow = client.newWorkflowStub(
                ExampleWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setTaskQueue("example-task-queue")
                        .build()
        );

        // Start the workflow execution
        workflow.execute();
    }
}
