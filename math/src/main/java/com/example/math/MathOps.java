package com.example.math;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MathOps {

    private final KafkaSender kafkaSender;

    @Autowired
    public MathOps(KafkaSender kafkaSender) {
        this.kafkaSender = kafkaSender;
    }

    /**
     * Parses the input message, calculates the result, and sends it back via Kafka.
     *
     * @param expression input string like "sum 3 4"
     */
    public void processAndSendResult(String expressionWithId) {
        try {
            String[] parts = expressionWithId.split("\\|", 2);
            String correlationId = parts[0];
            String expression = parts[1];

            String result = calculate(expression);
            kafkaSender.sendResult(correlationId + "|" + result);
        } catch (Exception e) {
            kafkaSender.sendResult("error|Error processing request");
        }
    }


    /**
     * Parses and calculates the expression.
     *
     * @param expression string like "sum 3 4"
     * @return the calculation result
     */
    public String calculate(String expression) {
    String[] parts = expression.trim().split("\\s+");
    if (parts.length != 3) {
        return "Error: Invalid expression format. Expected: operation operand1 operand2";
    }

    String operation = parts[0].toLowerCase();
    double operand1;
    double operand2;

    try {
        operand1 = Double.parseDouble(parts[1]);
        operand2 = Double.parseDouble(parts[2]);
    } catch (NumberFormatException e) {
        return "Error: Operands must be numeric.";
    }

    switch (operation) {
        case "sum":
            return Double.toString(operand1 + operand2);
        case "subtraction":
            return Double.toString(operand1 - operand2);
        case "multiplication":
            return Double.toString(operand1 * operand2);
        case "division":
            if (operand2 == 0) {
                return "Error: Division by zero is not allowed.";
            }
            return Double.toString(operand1 / operand2);
        default:
            return "Error: Unknown operation: " + operation;
        }
    }
}
