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
