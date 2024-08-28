package javafest.dlpadmin.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "refresh_tokens")
public class RefreshToken {
    @Id
    private String tokenId;
    private String userId;
    private String token;
    private Instant expiryDate;

    public RefreshToken(String userId, String token, Instant expiryDate) {
        this.userId = userId;
        this.token = token;
        this.expiryDate = expiryDate;
    }
}