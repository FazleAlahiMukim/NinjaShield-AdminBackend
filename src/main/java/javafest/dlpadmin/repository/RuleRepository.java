package javafest.dlpadmin.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import javafest.dlpadmin.model.Rule;

public interface RuleRepository extends MongoRepository<Rule, String> {

    List<Rule> findByDataId(String dataId);
    List<Rule> findByDataIdIn(List<String> dataIds);
    void deleteByDataId(String dataId);
}
