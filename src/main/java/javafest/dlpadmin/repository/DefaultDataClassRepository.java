package javafest.dlpadmin.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import javafest.dlpadmin.model.DefaultDataClass;

public interface DefaultDataClassRepository extends MongoRepository<DefaultDataClass, String> {

}
