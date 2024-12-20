package com.adrianojlt.temporaling.configs;

import com.adrianojlt.temporaling.activities.ExampleActivityImpl;
import com.adrianojlt.temporaling.workflows.ExampleWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.spring.boot.autoconfigure.properties.TemporalProperties;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties(TemporalProperties.class)
public class TemporalConfig {

    @Bean
    public WorkflowServiceStubs workflowServiceStubs(TemporalProperties temporalProperties) {

        var workflowServiceStubsOptions = WorkflowServiceStubsOptions.newBuilder()
                .setTarget(temporalProperties.getConnection().getTarget())
                .build();

        return WorkflowServiceStubs.newServiceStubs(workflowServiceStubsOptions);
    }

    @Bean
    public WorkflowClient workflowClient(TemporalProperties temporalProperties, WorkflowServiceStubs workflowServiceStubs) {

        var workflowClientOptions = WorkflowClientOptions.newBuilder()
                .setNamespace(temporalProperties.getNamespace())
                .build();

        return WorkflowClient.newInstance(workflowServiceStubs, workflowClientOptions);
    }

    @Bean
    public WorkerFactory workerFactory(WorkflowClient workflowClient) {
        return WorkerFactory.newInstance(workflowClient);
    }

    @Bean
    public ExampleActivityImpl exampleActivityImpl() {
        return new ExampleActivityImpl();
    }

    @Bean
    public List<Worker> registerWorkers(
            WorkerFactory workerFactory,
            ExampleActivityImpl exampleActivity) {

        Worker worker = workerFactory.newWorker("example-worker");

        worker.registerActivitiesImplementations(exampleActivity);
        worker.registerWorkflowImplementationTypes(ExampleWorkflowImpl.class);

        workerFactory.start();

        return List.of(worker);
    }
}
