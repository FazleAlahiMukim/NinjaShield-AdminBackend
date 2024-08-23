package javafest.dlpadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javafest.dlpadmin.model.Policy;
import javafest.dlpadmin.repository.PolicyRepository;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    public List<Policy> findByUserId(String userId) {
        return policyRepository.findByUserId(userId);
    }

    public Policy save(Policy policy) {
        return policyRepository.save(policy);
    }
}
