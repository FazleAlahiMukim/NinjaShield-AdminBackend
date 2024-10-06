package javafest.dlpadmin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import javafest.dlpadmin.dto.EventDto;
import javafest.dlpadmin.model.Device;
import javafest.dlpadmin.model.Event;
import javafest.dlpadmin.repository.EventRepository;
import javafest.dlpadmin.repository.RuleRepository;
import javafest.dlpadmin.repository.DataClassRepository;
import javafest.dlpadmin.repository.DeviceRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EventService {
    private EventRepository eventRepository;
    private DeviceRepository deviceRepository;
    private RuleRepository ruleRepository;
    private DataClassRepository dataClassRepository;

    public List<Event> findByUserId(String userId) {
        List<String> deviceIds = deviceRepository.findByUserId(userId).stream().map(Device::getDeviceId).toList();
        return eventRepository.findByDeviceIdIn(deviceIds);
    }

    public boolean save(String userId, Event event) {
        if (deviceRepository.findByUserId(userId).stream().anyMatch(device -> device.getDeviceId().equals(event.getDeviceId()))) {
            eventRepository.save(event);
            return true;
        }
        return false;
    }

    public boolean save(EventDto eventDto) {
        Event event = new Event(eventDto);
        
        deviceRepository.findById(event.getDeviceId()).ifPresent(device -> event.setDeviceName(device.getName()));

        ruleRepository.findById(eventDto.getRuleId()).ifPresent(rule -> {
            event.setRuleName(rule.getName());
            dataClassRepository.findById(rule.getDataId()).ifPresent(dataClass -> {
                event.setDataClassName(dataClass.getName());
            });
        });

        eventRepository.save(event);
        return true;
    }

    public boolean deleteEvent(String userId, String eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            if (deviceRepository.findByUserId(userId).stream().anyMatch(device -> device.getDeviceId().equals(event.getDeviceId()))) {
                eventRepository.deleteById(eventId);
                return true;
            }
        }
        return false;
    }
}
