package javafest.dlpadmin.controller;

import org.springframework.web.bind.annotation.RestController;

import javafest.dlpadmin.model.Destination;
import javafest.dlpadmin.model.FileCategory;
import javafest.dlpadmin.model.Policy;
import javafest.dlpadmin.service.PolicyService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping
    public ResponseEntity<Void> updatePolicyStatus(@RequestParam String policyId) {
        String authenticatedUserId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean status = policyService.updatePolicyStatus(authenticatedUserId, policyId);
        if (status) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePolicy(@RequestParam String policyId) {
        String authenticatedUserId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean status = policyService.deletePolicy(authenticatedUserId, policyId);
        if (status) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/destinations")
    public ResponseEntity<List<Destination>> getDestinations() {
        return new ResponseEntity<>(policyService.getDestinations(), HttpStatus.OK);
    }

    @GetMapping("/file-categories")
    public ResponseEntity<List<FileCategory>> getFileCategories() {
        return new ResponseEntity<>(policyService.getFileCategories(), HttpStatus.OK);
    }

}
