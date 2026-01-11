package org.example;

import org.example.models.Event;
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

public class StoreEvent {
    private Consumer<String, String> myConsumer;
    private ObjectMapper objectMapper;

    public StoreEvent(){
        Properties consumerConfig = new Properties();
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "event-tracker");
        consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        this.myConsumer = new KafkaConsumer<>(consumerConfig);
        this.objectMapper = new ObjectMapper();

        myConsumer.subscribe(Collections.singletonList("events"));
    }
    public void startConsuming(){
        System.out.println("🟢 Consumer is running and subscribed to events topic");
        try{
            while(true){
                ConsumerRecords<String, String> records = myConsumer.poll(Duration.ofMillis(1000));
                if (records.isEmpty()) {
                    continue;
                }

                for (ConsumerRecord<String, String> record : records) {
                    try {
                        String event_value = record.value();
                        Event event = objectMapper.readValue(event_value, Event.class);
                        System.out.println("📦 Received event: " + event.getEventId());
                    } catch (JsonProcessingException e) {
                        System.out.println("❌ Error: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("\n🔴 Stopping consumer");
        } finally {
            myConsumer.close();
        }
    }
}
