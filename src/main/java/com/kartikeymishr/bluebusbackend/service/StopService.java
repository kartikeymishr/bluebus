package com.kartikeymishr.bluebusbackend.service;

import com.kartikeymishr.bluebusbackend.dto.model.bus.StopDto;
import com.kartikeymishr.bluebusbackend.dto.request.StopRequest;
import com.kartikeymishr.bluebusbackend.model.bus.Stop;

import java.util.List;
import java.util.Set;

public interface StopService {

    StopDto createStop(StopRequest stopRequest);

    StopDto updateStopDetails(StopDto stopDto);

    void deleteStop(String stopCode);

    StopDto getStopByCode(String stopCode);

    Set<StopDto> getAllStops();
}
