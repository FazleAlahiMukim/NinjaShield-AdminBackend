package javafest.dlpadmin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafest.dlpadmin.model.DataClass;
import javafest.dlpadmin.model.Destination;
import javafest.dlpadmin.model.FileCategory;
import javafest.dlpadmin.model.Policy;
import javafest.dlpadmin.repository.DataClassRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatbotService {

    @Value("${openai.api.key}")
    private String API_KEY;

    @Autowired
    private PolicyService policyService;

    @Autowired
    private DataClassRepository dataClassRepository;

    private final String API_URL = "https://api.openai.com/v1/chat/completions";

    public Policy generatePolicy(String userId, String userPrompt) {
        List<String> fileCategories = policyService.getFileCategories().stream().map(FileCategory::getName).toList();
        List<String> destinations = policyService.getDestinations().stream().map(Destination::getName).toList();

        List<DataClass> dataClasses = dataClassRepository.findByUserIdAndIsActiveTrue(userId);
        List<String> dataClassNames = dataClasses.stream().map(DataClass::getName).toList();

        Policy policy = null;
        try {
            policy = executeGeneratePolicy(userPrompt, fileCategories, dataClassNames, destinations);
            policy.setUserId(userId);
            policy.setActive(true);
            policy.setEvents(0);
            policy.setLastUpdated(new Date());
            List<String> dataClassNamesInPolicy = policy.getDataClasses();
            List<String> dataIds = dataClasses.stream().filter(dataClass -> dataClassNamesInPolicy.contains(dataClass.getName())).map(DataClass::getDataId).toList();
            policy.setDataClasses(dataIds);
            policy = policyService.save(policy);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return policy;
    }

    private Policy executeGeneratePolicy(String userPrompt, List<String> fileCategories, List<String> dataClasses, List<String> destinations) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        Map<String, Object> jsonSchema = new HashMap<>();
        jsonSchema.put("name", "policy_creation");
        
        Map<String, Object> schema = new HashMap<>();
        schema.put("type", "object");
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("risk", createEnumProperty("string", new String[]{"high", "medium", "low"}));
        properties.put("action", createEnumProperty("string", new String[]{"block", "warn", "log"}));
        properties.put("name", Map.of("type", "string", "description", "A short name for the created policy."));
        properties.put("fileCategories", createEnumPropertyList(fileCategories));
        properties.put("dataClasses", createEnumPropertyList(dataClasses));
        properties.put("destinations", createEnumPropertyList(destinations));

        schema.put("properties", properties);
        schema.put("required", List.of("risk", "action", "name", "fileCategories", "dataClasses", "destinations"));
        schema.put("additionalProperties", false);

        jsonSchema.put("schema", schema);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o-mini");
        
        Map<String, Object> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", "You are an expert at structured data extraction. You will be given a text from an user who wants to create a policy for a DLP tool, convert it into the given structure.");
        
        Map<String, Object> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", userPrompt);
        
        requestBody.put("messages", List.of(systemMessage, userMessage));
        requestBody.put("response_format", Map.of("type", "json_schema", "json_schema", jsonSchema));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, request, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.readTree(response.getBody())
                                      .path("choices")
                                      .get(0)
                                      .path("message")
                                      .path("content")
                                      .asText();

        return objectMapper.readValue(content, Policy.class);
    }

    private Map<String, Object> createEnumProperty(String type, String[] values) {
        Map<String, Object> property = new HashMap<>();
        property.put("type", type);
        property.put("enum", values);
        return property;
    }

    private Map<String, Object> createEnumPropertyList(List<String> values) {
        Map<String, Object> items = new HashMap<>();
        items.put("type", "string");
        items.put("enum", values);
        
        Map<String, Object> property = new HashMap<>();
        property.put("type", "array");
        property.put("items", items);
        
        return property;
    }
}
