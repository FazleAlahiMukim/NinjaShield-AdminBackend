package javafest.dlpadmin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import javafest.dlpadmin.model.Device;
import javafest.dlpadmin.repository.DeviceRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DeviceService {
    private DeviceRepository deviceRepository;

    public String findById(String deviceId) {
        return deviceRepository.findById(deviceId).get().getUserId();
    }

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
