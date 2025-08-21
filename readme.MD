

Project with examples using Java with Temporal Orchestration Framework

Start the Temporal server:

```
temporal server start-dev --db-filename ~/temp/my_temporal.db --dynamic-config-value frontend.enableUpdateWorkflowExecution=true --dynamic-config-value frontend.enableUpdateWorkflowExecutionAsyncAccepted=true --ui-port 8080 --ip 0.0.0.0 
