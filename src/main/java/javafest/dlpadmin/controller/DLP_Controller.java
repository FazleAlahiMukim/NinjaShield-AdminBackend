package javafest.dlpadmin.controller;

import org.springframework.web.bind.annotation.RestController;

import javafest.dlpadmin.dto.EventDto;
import javafest.dlpadmin.model.Policy;
import javafest.dlpadmin.model.Rule;
import javafest.dlpadmin.service.DeviceService;
import javafest.dlpadmin.service.EventService;
import javafest.dlpadmin.service.PolicyService;
import javafest.dlpadmin.service.RuleService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@RestController
@RequestMapping("/api/dlp")
public class DLP_Controller {
    private final DeviceService deviceService;
    private final PolicyService policyService;
    private final RuleService ruleService;
    private final EventService eventService;

    @GetMapping("/admin")
    public ResponseEntity<String> getAdminId(@RequestParam String deviceId) {
        return new ResponseEntity<>(deviceService.findById(deviceId), HttpStatus.OK);
    }

    @GetMapping("/policies")
    public ResponseEntity<List<Policy>> getPolicies(@RequestParam String userId) {
        return new ResponseEntity<>(policyService.findByUserIdAndIsActiveTrue(userId), HttpStatus.OK);
    }

    @GetMapping("/rules")
    public ResponseEntity<List<Rule>> getRules(@RequestParam String dataId) {
        return new ResponseEntity<>(ruleService.findByDataId(dataId), HttpStatus.OK);
    }

    @PostMapping("/event")
    public ResponseEntity<Void> saveEvent(@RequestBody EventDto eventDto) {
        boolean result = eventService.save(eventDto);
        if (result) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
