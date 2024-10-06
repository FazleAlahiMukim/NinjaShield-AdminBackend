package javafest.dlpadmin.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javafest.dlpadmin.dto.EventDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "events")
public class Event {
    @Id
    private String eventId;
    private String deviceId;
    private String deviceName;
    private String risk;
    private String action;
    private String policyName;
    private String dataClassName;
    private String ruleName;
    private int occurrences;
    private String fileName;
    private String destinationType;
    private String destination;
    private String source;
    private Date time;

    public Event(EventDto eventDto) {
        this.deviceId = eventDto.getDeviceId();
        this.risk = eventDto.getRisk();
        this.action = eventDto.getAction();
        this.policyName = eventDto.getPolicyName();
        this.occurrences = eventDto.getOccurrences();
        this.fileName = eventDto.getFileName();
        this.destinationType = eventDto.getDestinationType();
        this.destination = eventDto.getDestination();
        this.source = eventDto.getSource();
        this.time = eventDto.getTime();
    }
}