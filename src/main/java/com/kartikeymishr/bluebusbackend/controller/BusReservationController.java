package com.kartikeymishr.bluebusbackend.controller;

import com.kartikeymishr.bluebusbackend.dto.model.bus.StopDto;
import com.kartikeymishr.bluebusbackend.service.BusReservationService;
import com.kartikeymishr.bluebusbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/reservation")
public class BusReservationController {

    @Autowired
    private BusReservationService busReservationService;

    @Autowired
    private UserService userService;
}
