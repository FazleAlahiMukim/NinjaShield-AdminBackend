package javafest.dlpadmin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import javafest.dlpadmin.model.Destination;

public interface DestinationRepository extends MongoRepository<Destination, String> {

}
