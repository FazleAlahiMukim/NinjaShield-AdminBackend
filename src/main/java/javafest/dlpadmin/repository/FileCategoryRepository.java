package javafest.dlpadmin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import javafest.dlpadmin.model.FileCategory;

public interface FileCategoryRepository extends MongoRepository<FileCategory, String> {

}
