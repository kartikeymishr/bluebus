package com.kartikeymishr.bluebusbackend.service;

import com.kartikeymishr.bluebusbackend.dto.model.bus.BusDto;

public interface BusService {

    BusDto addBus(BusDto busDto);

    BusDto removeBus(String busCode);

    BusDto updateBusDetails(BusDto busDto);
}
