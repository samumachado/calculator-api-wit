package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.TimeUnit;

@RestController
public class Controller {

    @Autowired
    private KafkaSender kafkaSender;

    @Autowired
    private KafkaReceiver kafkaResultReceiver;

    @GetMapping("/sum")
    public ResponseEntity<?> sum(@RequestParam int a, @RequestParam int b) {
        return handleRequest("sum", a, b);
    }

    @GetMapping("/subtraction")
    public ResponseEntity<?> subtraction(@RequestParam int a, @RequestParam int b) {
        return handleRequest("subtraction", a, b);
    }

    @GetMapping("/multiplication")
    public ResponseEntity<?> multiplication(@RequestParam int a, @RequestParam int b) {
        return handleRequest("multiplication", a, b);
    }

    @GetMapping("/division")
    public ResponseEntity<?> division(@RequestParam int a, @RequestParam int b) {
        return handleRequest("division", a, b);
    }

    private ResponseEntity<?> handleRequest(String operation, int a, int b) {
        String correlationId = java.util.UUID.randomUUID().toString();
        String message = operation + " " + a + " " + b;
        kafkaResultReceiver.prepareCorrelation(correlationId); // must prepare first
        kafkaSender.send(message, correlationId);

        try {
            String result = kafkaResultReceiver.getResult(correlationId, 5, TimeUnit.SECONDS);
            if (result.startsWith("Error:")) {
                return ResponseEntity.status(400).body(java.util.Map.of("error", result.substring(7).trim()));
            }
            return ResponseEntity.ok(java.util.Map.of("result", Double.parseDouble(result)));
        } catch (Exception e) {
            return ResponseEntity.status(504).body(java.util.Map.of("error", "Timeout waiting for result"));
        }
    }
}
