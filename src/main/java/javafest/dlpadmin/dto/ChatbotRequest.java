package javafest.dlpadmin.dto;

import lombok.Data;

@Data
public class ChatbotRequest {
    private String userId;
    private String prompt;
}

