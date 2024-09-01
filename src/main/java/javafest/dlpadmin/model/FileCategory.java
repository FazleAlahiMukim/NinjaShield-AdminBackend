package javafest.dlpadmin.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "file_categories")
public class FileCategory {
    @Id
    private String fileCategoryId;
    private String name;
}

