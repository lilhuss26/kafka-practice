package org.example;

import org.example.models.Event;
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

public class EventProducer {
    private Producer <String, String> myProducer;
    private ObjectMapper objectMapper = new ObjectMapper();

    public EventProducer(){
        Properties myProducerConfig = new Properties();

        myProducerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        myProducerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        myProducerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        this.myProducer = new KafkaProducer<>(myProducerConfig);
    }
     public void sendEvent(){
         Event my_event = Event.builder()
                 .eventId(UUID.randomUUID().toString())
                 .player("Messi")
                 .event("Goal")
                 .minute(76)
                 .build();

        final String my_event_values;
        try {
            my_event_values = objectMapper.writeValueAsString(my_event);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }
         ProducerRecord<String, String> a_record = new ProducerRecord<>("events", my_event_values);
        myProducer.send(a_record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e!=null){
                    System.out.println("❌ Adding event failed: " + e.getMessage());
                }else{
                    System.out.println("✅ Event created " + my_event_values);
                    System.out.println("✅ Event created to " + recordMetadata.topic() +
                            " : partition " + recordMetadata.partition() +
                            " : at offset " + recordMetadata.offset());
                }
            }
        });
    }

}
