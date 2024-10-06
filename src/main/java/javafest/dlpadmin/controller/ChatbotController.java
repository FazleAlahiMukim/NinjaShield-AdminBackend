package javafest.dlpadmin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javafest.dlpadmin.dto.ChatbotRequest;
import javafest.dlpadmin.model.Policy;
import javafest.dlpadmin.service.ChatbotService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {
    private final ChatbotService chatbotService;

    @PostMapping
    public ResponseEntity<Policy> chatbot(@RequestBody ChatbotRequest request) {
        String authenticatedUserId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!authenticatedUserId.equals(request.getUserId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Policy policy = chatbotService.generatePolicy(request.getUserId(), request.getPrompt());
        return new ResponseEntity<>(policy, HttpStatus.OK);
    }
    
}
