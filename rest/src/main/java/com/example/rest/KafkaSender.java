package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSender {

    private static final String TOPIC = "calculator-operations";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String message, String correlationId) {
        String fullMessage = correlationId + "|" + message;
        kafkaTemplate.send(TOPIC, fullMessage);
        System.out.println("Sent message to Kafka: " + fullMessage);
    }
}
