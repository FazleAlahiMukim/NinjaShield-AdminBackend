package javafest.dlpadmin.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import javafest.dlpadmin.model.Policy;

public interface PolicyRepository extends MongoRepository<Policy, String> {

    List<Policy> findByUserId(String userId);
    List<Policy> findByUserIdAndIsActiveTrue(String userId);
}
