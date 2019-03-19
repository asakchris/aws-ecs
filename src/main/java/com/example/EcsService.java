package com.example;

import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.core.retry.RetryPolicy;
import software.amazon.awssdk.services.ecs.EcsAsyncClient;
import software.amazon.awssdk.services.ecs.model.DesiredStatus;
import software.amazon.awssdk.services.ecs.model.ListTasksRequest;
import software.amazon.awssdk.services.ecs.model.ListTasksResponse;

import java.util.Optional;
import java.util.concurrent.*;

public class EcsService {
    public static void main(String[] args) {
        System.out.println("START");

        final ClientOverrideConfiguration conf = ClientOverrideConfiguration.builder().retryPolicy(RetryPolicy.none()).build();
        final EcsAsyncClient ecsAsyncClient = EcsAsyncClient.builder().overrideConfiguration(conf).build();

        String cluster = System.getProperty("akka.discovery.aws-api-ecs-async.cluster", "my-cluster");
        String serviceName = System.getProperty("akka.management.cluster.bootstrap.contact-point-discovery.service-name", "my-service");
        System.out.println("cluster: " + cluster + ", serviceName: " + serviceName);

        Optional<String> pageTaken = Optional.empty();

        CompletableFuture<ListTasksResponse> taskArns = getTaskArns(ecsAsyncClient, cluster, serviceName, pageTaken);
        try {
            final ListTasksResponse listTasksResponse = taskArns.get(1, TimeUnit.MINUTES);
            final String task = listTasksResponse.nextToken();
            System.out.println("task: " + task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        System.out.println("END");
    }

    private static CompletableFuture<ListTasksResponse> getTaskArns(EcsAsyncClient ecsClient, String cluster, String serviceName, Optional<String> pageTaken) {
        final CompletableFuture<ListTasksResponse> listTasksResponse = ecsClient
                .listTasks(
                        ListTasksRequest
                                .builder()
                                .cluster(cluster)
                                .serviceName(serviceName)
                                .nextToken(pageTaken.orElse(null))
                                .desiredStatus(DesiredStatus.RUNNING)
                                .build()
                );

        return listTasksResponse;
    }
}
