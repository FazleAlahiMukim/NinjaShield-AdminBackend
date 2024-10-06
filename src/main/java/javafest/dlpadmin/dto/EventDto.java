package javafest.dlpadmin.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventDto {
    private String deviceId;
    private String risk;
    private String action;
    private String policyName;
    private String ruleId;
    private int occurrences;
    private String fileName;
    private String destinationType;
    private String destination;
    private String source;
    private Date time;
}
