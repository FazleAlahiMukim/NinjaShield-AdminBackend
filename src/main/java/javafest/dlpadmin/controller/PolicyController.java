package javafest.dlpadmin.controller;

import org.springframework.web.bind.annotation.RestController;

import javafest.dlpadmin.model.Policy;
import javafest.dlpadmin.service.PolicyService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
@RequestMapping("/api/policy")
public class PolicyController {
    private final PolicyService policyService;

    @GetMapping
    public ResponseEntity<List<Policy>> findByUserId(@RequestParam String userId) {
        String authenticatedUserId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!authenticatedUserId.equals(userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(policyService.findByUserId(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Policy> save(@RequestBody Policy policy) {
        String authenticatedUserId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!authenticatedUserId.equals(policy.getUserId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(policyService.save(policy), HttpStatus.CREATED);
    }
}
