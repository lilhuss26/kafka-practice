package org.example;

import org.example.models.Order;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Properties;
import java.util.Collections;
import java.time.Duration;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Tracker {

    private Consumer<String, String> consumer;
    private ObjectMapper objectMapper;

    public Tracker() {
        Properties consumerConfig = new Properties();
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "order-tracker");
        consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        this.consumer = new KafkaConsumer<>(consumerConfig);
        this.objectMapper = new ObjectMapper();

        consumer.subscribe(Collections.singletonList("orders"));
    }

    public void startConsuming() {
        System.out.println("🟢 Consumer is running and subscribed to orders topic");

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                if (records.isEmpty()) {
                    continue;
                }

                for (ConsumerRecord<String, String> record : records) {
                    try {
                        String value = record.value();
                        Order order = objectMapper.readValue(value, Order.class);
                        System.out.println("📦 Received order: " + order.getQuantity() +
                                " x " + order.getItem() +
                                " from " + order.getUser());
                    } catch (JsonProcessingException e) {
                        System.out.println("❌ Error: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("\n🔴 Stopping consumer");
        } finally {
            consumer.close();
        }
    }
}