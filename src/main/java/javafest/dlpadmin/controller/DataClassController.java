package javafest.dlpadmin.controller;

import org.springframework.web.bind.annotation.RestController;

import javafest.dlpadmin.model.DataClass;
import javafest.dlpadmin.model.Rule;
import javafest.dlpadmin.service.DataClassService;
import javafest.dlpadmin.service.RuleService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/data-class")
public class DataClassController {
    private final DataClassService dataClassService;
    private final RuleService ruleService;

    @GetMapping
    public ResponseEntity<List<DataClass>> findByUserId(@RequestParam String userId) {
        String authenticatedUserId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!authenticatedUserId.equals(userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(dataClassService.findByUserId(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DataClass> save(@RequestBody DataClass dataClass) {
        String authenticatedUserId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!authenticatedUserId.equals(dataClass.getUserId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(dataClassService.save(dataClass), HttpStatus.CREATED);
    }

    @PostMapping("/rule")
    public ResponseEntity<Rule> saveRule(@RequestBody Rule rule) {
        String authenticatedUserId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!dataClassService.validateUserAndDataId(authenticatedUserId, rule.getDataId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(ruleService.save(rule), HttpStatus.CREATED);  
    }
    
    

}
