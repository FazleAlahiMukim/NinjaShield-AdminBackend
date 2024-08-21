package javafest.dlpadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javafest.dlpadmin.model.DataClass;
import javafest.dlpadmin.repository.DataClassRepository;

@Service
public class DataClassService {

    @Autowired
    private DataClassRepository dataClassRepository;

    public List<DataClass> findByUserId(String userId) {
        return dataClassRepository.findByUserId(userId);
    }

    public DataClass save(DataClass dataClass) {
        return dataClassRepository.save(dataClass);
    }

    public boolean validateUserAndDataId(String userId, String dataId) {
        return dataClassRepository.existsByUserIdAndDataId(userId, dataId);
    }

}
