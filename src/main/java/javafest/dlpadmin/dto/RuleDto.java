package javafest.dlpadmin.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
class element {
    private String type;
    private List<String> text;
}

@Data
@NoArgsConstructor
public class RuleDto {
    private String name;
    private int occurrences;
    private List<element> elements;
}
