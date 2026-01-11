package org.example;

public class Main {
    public static void main(String[] args) {
        // First: Send the event
        EventProducer producer = new EventProducer();
        producer.sendEvent();

        // Second: Start consuming
        EventConsumer consumer = new EventConsumer();
        consumer.startConsuming();
    }
}