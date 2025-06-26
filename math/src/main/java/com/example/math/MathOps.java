package com.example.math;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode; // Adicione isso no topo, se ainda não tiver


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
     * @param expressionWithId input string like "id123|sum 3 4"
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
     * Parses and calculates the expression using BigDecimal.
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
        BigDecimal operand1;
        BigDecimal operand2;

        try {
            operand1 = new BigDecimal(parts[1]);
            operand2 = new BigDecimal(parts[2]);
        } catch (NumberFormatException e) {
            return "Error: Operands must be numeric.";
        }

        try {
            switch (operation) {
                case "sum":
                    return operand1.add(operand2).toPlainString();
                case "subtraction":
                    return operand1.subtract(operand2).toPlainString();
                case "multiplication":
                    return operand1.multiply(operand2).toPlainString();
                case "division":
                    if (operand2.compareTo(BigDecimal.ZERO) == 0) {
                        return "Error: Division by zero is not allowed.";
                    }
                    // Scale and rounding mode to avoid ArithmeticException on non-terminating decimal
                    return operand1.divide(operand2, 20, RoundingMode.HALF_UP).toPlainString();
                default:
                    return "Error: Unknown operation: " + operation;
            }
        } catch (ArithmeticException e) {
            return "Error: " + e.getMessage();
        }
    }
}
