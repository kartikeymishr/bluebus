package com.kartikeymishr.bluebusbackend.controller;

import com.kartikeymishr.bluebusbackend.dto.model.bus.StopDto;
import com.kartikeymishr.bluebusbackend.dto.request.StopRequest;
import com.kartikeymishr.bluebusbackend.service.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/stop")
public class StopController {

    @Autowired
    private StopService stopService;

    @PostMapping("/create")
    public ResponseEntity<StopDto> createStop(StopRequest stopRequest) {
        return ResponseEntity.ok(stopService.createStop(stopRequest));
    }

    @GetMapping("/get/all")
    public ResponseEntity<Set<StopDto>> getAllStops() {
        return ResponseEntity.ok(stopService.getAllStops());
    }

    @GetMapping("/get/{code}")
    public ResponseEntity<StopDto> getStopById(@PathVariable String code) {
        return ResponseEntity.ok(stopService.getStopByCode(code));
    }

    @DeleteMapping("/delete/{code}")
    public ResponseEntity<String> deleteStopByCode(@PathVariable String code) {
        stopService.deleteStop(code);

        return ResponseEntity.ok("Deleted Successfully");
    }
}
