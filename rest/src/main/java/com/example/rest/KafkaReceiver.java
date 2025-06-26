package com.example.rest;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;

@Component
public class KafkaReceiver {

    private final Map<String, CompletableFuture<String>> pendingResults = new ConcurrentHashMap<>();

    public void prepareCorrelation(String correlationId) {
        pendingResults.put(correlationId, new CompletableFuture<>());
    }

    public String getResult(String correlationId, long timeout, TimeUnit unit) throws Exception {
        CompletableFuture<String> future = pendingResults.get(correlationId);
        if (future == null) {
            throw new IllegalStateException("No future for correlationId: " + correlationId);
        }
        try {
            return future.get(timeout, unit);
        } finally {
            pendingResults.remove(correlationId); // Cleanup
        }
    }

    @KafkaListener(topics = "math-results", groupId = "rest")
    public void listen(ConsumerRecord<String, String> record) {
        String message = record.value();
        System.out.println("Received from Kafka: " + message);

        String[] parts = message.split("\\|", 2);
        if (parts.length != 2) {
            System.err.println("Malformed message (missing correlation ID): " + message);
            return;
        }

        String correlationId = parts[0];
        String result = parts[1];

        CompletableFuture<String> future = pendingResults.get(correlationId);
        if (future != null) {
            future.complete(result);
        } else {
            System.err.println("No pending request for correlationId: " + correlationId);
        }
    }
}
