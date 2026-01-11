package org.example.models;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    private String eventId;
    private String player;
    private String event;
    private int minute;
}
