package com.kartikeymishr.bluebusbackend.service.impl;

import com.kartikeymishr.bluebusbackend.dto.model.bus.BusDto;
import com.kartikeymishr.bluebusbackend.repository.bus.BusRepository;
import com.kartikeymishr.bluebusbackend.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusServiceImpl implements BusService {

    @Autowired
    private BusRepository busRepository;

    @Override
    public BusDto addBus(BusDto busDto) {
        return null;
    }

    @Override
    public BusDto removeBus(String busCode) {
        return null;
    }

    @Override
    public BusDto updateBusDetails(BusDto busDto) {
        return null;
    }
}
