package javafest.dlpadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javafest.dlpadmin.model.Device;
import javafest.dlpadmin.repository.DeviceRepository;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public List<Device> findByUserId(String userId) {
        return deviceRepository.findByUserId(userId);
    }

    public List<Device> findByEmail(String email) {
        return deviceRepository.findByEmail(email);
    }

    public Device save(Device device) {
        return deviceRepository.save(device);
    }
}
