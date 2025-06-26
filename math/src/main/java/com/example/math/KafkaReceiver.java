package com.example.math;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaReceiver {

    @Autowired
    private MathOps mathProcessor;

    @KafkaListener(topics = "calculator-operations", groupId = "math-group")
    public void listen(String message) {
        System.out.println("Received message in math module: " + message);
        mathProcessor.processAndSendResult(message);
    }
}
