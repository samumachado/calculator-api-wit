A simple distributed calculator using Spring Boot and Apache Kafka. It includes two microservices:

REST Module –> exposes HTTP endpoints for math operations and sends them via Kafka.
Math Module –> consumes operations from Kafka, computes the result, and sends it back to REST via Kafka.

---

Requirements

- Java 17+
- Maven
- Docker

---

RUN THE PROJECT

1. Clone the Repository (https://github.com/samumachado/calculator-api-wip.git)
2. In the root directory run "mvn clean install"
3. Run the following commands:
  docker-compose build
  docker-compose up

Test:
curl -i "http://localhost:8081/sum?a=2&b=4" 
curl -i "http://localhost:8081/subtraction?a=-3&b=-10.4444" 
curl -i "http://localhost:8081/multiplication?a=-3&b=-10.33" 
curl -i "http://localhost:8081/division?a=-3&b=0" 
curl -i "http://localhost:8081/division?a=-3&b=4" 
