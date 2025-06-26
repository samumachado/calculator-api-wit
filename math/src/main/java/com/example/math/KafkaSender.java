package com.example.math;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.kafka.core.KafkaTemplate;


@Component
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final String resultTopic = "math-results";

    public void sendResult(String result) {
        String message = result;
        kafkaTemplate.send(resultTopic, message);
        System.out.println("Sent result: " + message);
    }
}
