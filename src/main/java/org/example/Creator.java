package org.example;
import org.example.models.Order;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.common.serialization.StringSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Properties;
import java.util.UUID;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Creator {

    private Producer<String, String> producer;

    public Creator() {
        Properties producerConfig = new Properties();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        this.producer = new KafkaProducer<>(producerConfig);
    }

    public void sendOrder() {
        Order order = new Order(
                UUID.randomUUID().toString(),
                "lara",
                "frozen yogurt",
                10
        );

        ObjectMapper objectMapper = new ObjectMapper();
        final String order_value;  // Make it final

        try {
            order_value = objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        ProducerRecord<String, String> record = new ProducerRecord<>("orders", order_value);

        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception != null) {
                    System.out.println("❌ Delivery failed: " + exception.getMessage());
                } else {
                    System.out.println("✅ Delivered " + order_value);
                    System.out.println("✅ Delivered to " + metadata.topic() +
                            " : partition " + metadata.partition() +
                            " : at offset " + metadata.offset());
                }
            }
        });
    }
}