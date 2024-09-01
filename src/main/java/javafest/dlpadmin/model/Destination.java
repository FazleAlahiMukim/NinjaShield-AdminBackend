package javafest.dlpadmin.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "destinations")
public class Destination {
    @Id
    private String destinationId;
    private String name;
}

