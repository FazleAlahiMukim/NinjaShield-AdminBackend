package javafest.dlpadmin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String organization;
    private String token;
}

