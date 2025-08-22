package com.adrianojlt.temporaling.configs;

import com.adrianojlt.temporaling.activities.ExampleActivityImpl;
import com.adrianojlt.temporaling.activities.SendEmailActivityImpl;
import com.adrianojlt.temporaling.enums.TaskQueues;
import com.adrianojlt.temporaling.workflows.ExampleWorkflowImpl;
import com.adrianojlt.temporaling.workflows.SendEmailWorkflowImpl;
import io.temporal.client.WorkflowClient;
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
    public WorkerFactory workerFactory(WorkflowClient workflowClient) {
        return WorkerFactory.newInstance(workflowClient);
    }

    @Bean
    public ExampleActivityImpl exampleActivityImpl() {
        return new ExampleActivityImpl();
    }

    @Bean
    public SendEmailActivityImpl sendEmailActivityImpl() {
        return new SendEmailActivityImpl();
    }

    @Bean
    public List<Worker> registerWorkers(
            WorkerFactory workerFactory,
            ExampleActivityImpl exampleActivity,
            SendEmailActivityImpl sendEmailActivity) {

        Worker exampleWorker = workerFactory.newWorker(TaskQueues.EXAMPLE_WORKFLOW_TASK_QUEUE.name());
        Worker emailWorker = workerFactory.newWorker(TaskQueues.EMAIL_WORKFLOW_TASK_QUEUE.name());

        exampleWorker.registerActivitiesImplementations(exampleActivity);
        exampleWorker.registerWorkflowImplementationTypes(ExampleWorkflowImpl.class);

        emailWorker.registerActivitiesImplementations(sendEmailActivity);
        emailWorker.registerWorkflowImplementationTypes(SendEmailWorkflowImpl.class);

        workerFactory.start();

        return List.of(exampleWorker, emailWorker);
    }
}
