package javafest.dlpadmin.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import javafest.dlpadmin.model.DataClass;

public interface DataClassRepository extends MongoRepository<DataClass, String> {

    List<DataClass> findByUserId(String userId);
    List<DataClass> findByUserIdAndIsActiveTrue(String userId);
    boolean existsByUserIdAndDataId(String userId, String dataId);
}
