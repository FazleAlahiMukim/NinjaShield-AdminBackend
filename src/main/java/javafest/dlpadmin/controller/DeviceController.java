package javafest.dlpadmin.controller;

import org.springframework.web.bind.annotation.RestController;

import javafest.dlpadmin.model.Device;
import javafest.dlpadmin.service.DeviceService;
import javafest.dlpadmin.service.AuthService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
@RequestMapping("/api/device")
public class DeviceController {
    private final DeviceService deviceService;

    @GetMapping
    public ResponseEntity<List<Device>> findByUserId(@RequestParam String userId) {
        String authenticatedUserId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!authenticatedUserId.equals(userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(deviceService.findByUserId(userId), HttpStatus.OK);
    }

    @PostMapping("/approve")
    public ResponseEntity<Device> approve(@RequestBody Device device) {
        String authenticatedUserId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!authenticatedUserId.equals(device.getUserId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(deviceService.save(device), HttpStatus.OK);
    }

    @PostMapping("/reject")
    public ResponseEntity<Void> reject(@RequestBody Device device) {
        String authenticatedUserId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!authenticatedUserId.equals(device.getUserId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        deviceService.delete(device);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Device> register(@RequestBody Device device) {
        return new ResponseEntity<>(deviceService.save(device), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Device> save(@RequestBody Device device) {
        String authenticatedUserId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!authenticatedUserId.equals(device.getUserId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(deviceService.save(device), HttpStatus.CREATED);
    }
}
