package javafest.dlpadmin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import javafest.dlpadmin.model.Policy;
import javafest.dlpadmin.repository.PolicyRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PolicyService {
    private PolicyRepository policyRepository;

    public List<Policy> findByUserId(String userId) {
        return policyRepository.findByUserId(userId);
    }

    public Policy save(Policy policy) {
        return policyRepository.save(policy);
    }
}
