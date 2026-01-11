# Kafka Practice Project

A simple Java application demonstrating Apache Kafka producer-consumer pattern with a football match events use case.

## Project Structure

```
src/main/java/org/example/
├── EventConsumer.java   # Kafka consumer implementation
├── EventProducer.java   # Kafka producer implementation
├── Main.java           # Application entry point
└── models/
    └── Event.java      # Event data model
```

## Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- Maven

## Getting Started

### 1. Start Kafka with Docker

```bash
docker-compose up -d
```

This will start a single-node Kafka broker with KRaft (Kafka Raft) mode enabled.

### 2. Build the project

```bash
mvn clean package
```

### 3. Run the application

```bash
mvn exec:java -Dexec.mainClass="com.practice.kafka.Main"
```

The application will:
1. Create a Kafka producer and send a sample football match event
2. Create a Kafka consumer to receive and process the event

## Event Model

The application uses a simple `Event` model with the following fields:
- `eventId`: Unique identifier for the event
- `player`: Name of the player involved
- `event`: Type of event (e.g., GOAL, YELLOW_CARD, etc.)
- `minute`: Match minute when the event occurred

## Kafka Configuration

- **Bootstrap Server**: `localhost:9092`
- **Topic**: `match-events`
- **Partitions**: 3
- **Replication Factor**: 1 (for development)

## How It Works

1. The `EventProducer` creates and sends a sample football match event to the `match-events` topic.
2. The `EventConsumer` subscribes to the same topic and processes incoming events.
3. The consumer runs in a separate thread and continues to listen for events until the application is stopped.

## Clean Up

To stop and remove the Kafka container:

```bash
docker-compose down -v
```

## License

This project is open source and available under the [MIT License](LICENSE).
