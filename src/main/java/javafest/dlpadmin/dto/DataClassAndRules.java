package javafest.dlpadmin.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataClassAndRules {
    private String dataId;
    private String userId;
    @JsonProperty("isActive")
    private boolean isActive;
    private String name;
    private String description;
    private int events;
    private Date lastUpdated;
    private List<RuleDto> rules;
}
