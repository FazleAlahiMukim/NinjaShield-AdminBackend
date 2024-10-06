package javafest.dlpadmin.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import javafest.dlpadmin.model.Event;

public interface EventRepository extends MongoRepository<Event, String> {

    List<Event> findByDeviceIdIn(List<String> deviceIds);
}
