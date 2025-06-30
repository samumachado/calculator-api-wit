# Description

A simple distributed calculator using Spring Boot and Apache Kafka.

REST Module –> exposes HTTP endpoints for math operations and sends them via Kafka.
Math Module –> consumes operations from Kafka, computes the result, and sends it back to REST via Kafka.

---

### Requirements

- Docker

---

# RUN THE PROJECT

1. Clone the Repository (https://github.com/samumachado/calculator-api-wip.git)
2. In the root directory, run the following command:
   docker-compose up --build

note: Docker must be running

---

# TEST THE PROJECT: (with another terminal, for example)

## SIMPLE OPERATIONS

### Addition

curl -i "http://localhost:8081/sum?a=3&b=2"

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 30 Jun 2025 10:13:48 GMT

{"result":"5"}

### Subtraction

curl -i "http://localhost:8081/subtraction?a=3&b=2"

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 30 Jun 2025 10:14:35 GMT

{"result":"1"}

### Multiplication

curl -i "http://localhost:8081/multiplication?a=3&b=2"

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 30 Jun 2025 10:15:16 GMT

{"result":"6"}

### Division

curl -i "http://localhost:8081/division?a=3&b=2"

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 30 Jun 2025 10:16:24 GMT

{"result":"1.50000000000000000000"}

## DECIMALS

### Addition with decimals

curl -i "http://localhost:8081/sum?a=2.5&b=3.75"

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 30 Jun 2025 10:17:30 GMT

{"result":"6.25"}

### Division with decimals

curl -i "http://localhost:8081/division?a=5.5&b=2.2"

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 30 Jun 2025 10:18:28 GMT

{"result":"2.50000000000000000000"}

## NEGATIVES

### Subtraction with negatives

curl -i "http://localhost:8081/subtraction?a=-3&b=-2"

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 30 Jun 2025 10:18:41 GMT

{"result":"-1"}

### Multiplication with one negative

curl -i "http://localhost:8081/multiplication?a=-3&b=2"

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 30 Jun 2025 10:19:10 GMT

{"result":"-6"}%

### Division with negative and zero (edge case)

curl -i "http://localhost:8081/division?a=-3&b=0"

HTTP/1.1 400 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 30 Jun 2025 10:19:34 GMT
Connection: close

{"error":"Division by zero is not allowed."}

## SCIENTIFIC NOTATION

### Multiplication with scientific notation

curl -i "http://localhost:8081/multiplication?a=1e3&b=2e-2"

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 30 Jun 2025 10:19:57 GMT

{"result":"20"}

### Division with scientific notation

curl -i "http://localhost:8081/division?a=5e4&b=2.5e2"

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 30 Jun 2025 10:20:19 GMT

{"result":"200.00000000000000000000"}




