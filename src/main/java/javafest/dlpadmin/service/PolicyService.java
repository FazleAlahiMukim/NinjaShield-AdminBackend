package javafest.dlpadmin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import javafest.dlpadmin.model.Destination;
import javafest.dlpadmin.model.FileCategory;
import javafest.dlpadmin.model.Policy;
import javafest.dlpadmin.repository.DestinationRepository;
import javafest.dlpadmin.repository.FileCategoryRepository;
import javafest.dlpadmin.repository.PolicyRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PolicyService {
    private PolicyRepository policyRepository;
    private DestinationRepository destinationRepository;
    private FileCategoryRepository fileCategoryRepository;

    public List<Policy> findByUserId(String userId) {
        return policyRepository.findByUserId(userId);
    }

    public List<Policy> findByUserIdAndIsActiveTrue(String userId) {
        return policyRepository.findByUserIdAndIsActiveTrue(userId);
    }

    public Policy save(Policy policy) {
        return policyRepository.save(policy);
    }

    public boolean updatePolicyStatus(String userId, String policyId) {
        Optional<Policy> policyOptional = policyRepository.findById(policyId);
        if (policyOptional.isPresent() && policyOptional.get().getUserId().equals(userId)) {
            Policy policy = policyOptional.get();
            policy.setActive(!policy.isActive());
            policyRepository.save(policy);
            return true;
        } else
            return false;
    }

    public boolean deletePolicy(String userId, String policyId) {
        Optional<Policy> policyOptional = policyRepository.findById(policyId);
        if (policyOptional.isPresent() && policyOptional.get().getUserId().equals(userId)) {
            policyRepository.deleteById(policyId);
            return true;
        }
        return false;
    }

    public List<Destination> getDestinations() {
        return destinationRepository.findAll();
    }

    public List<FileCategory> getFileCategories() {
        return fileCategoryRepository.findAll();
    }
}
