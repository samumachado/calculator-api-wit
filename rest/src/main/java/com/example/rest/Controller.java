package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class Controller {

    @Autowired
    private KafkaSender kafkaSender;

    @Autowired
    private KafkaReceiver kafkaResultReceiver;

    @GetMapping("/sum")
    public ResponseEntity<?> sum(@RequestParam String a, @RequestParam String b) {
        return handleRequest("sum", a, b);
    }

    @GetMapping("/subtraction")
    public ResponseEntity<?> subtraction(@RequestParam String a, @RequestParam String b) {
        return handleRequest("subtraction", a, b);
    }

    @GetMapping("/multiplication")
    public ResponseEntity<?> multiplication(@RequestParam String a, @RequestParam String b) {
        return handleRequest("multiplication", a, b);
    }

    @GetMapping("/division")
    public ResponseEntity<?> division(@RequestParam String a, @RequestParam String b) {
        return handleRequest("division", a, b);
    }

    private ResponseEntity<?> handleRequest(String operation, String a, String b) {
        String correlationId = UUID.randomUUID().toString();
        String message = operation + " " + a + " " + b;

        kafkaResultReceiver.prepareCorrelation(correlationId);
        kafkaSender.send(message, correlationId);

        try {
            String result = kafkaResultReceiver.getResult(correlationId, 5, TimeUnit.SECONDS);

            if (result.startsWith("Error:")) {
                return ResponseEntity.status(400).body(Map.of("error", result.substring(7).trim()));
            }
            // Return result as a String, no parsing here
            return ResponseEntity.ok(Map.of("result", result));
        } catch (Exception e) {
            return ResponseEntity.status(504).body(Map.of("error", "Timeout waiting for result"));
        }
    }
}
