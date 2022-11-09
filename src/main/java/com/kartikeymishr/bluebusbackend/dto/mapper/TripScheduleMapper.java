package com.kartikeymishr.bluebusbackend.dto.mapper;

import com.kartikeymishr.bluebusbackend.dto.model.bus.TripScheduleDto;
import com.kartikeymishr.bluebusbackend.model.bus.Trip;
import com.kartikeymishr.bluebusbackend.model.bus.TripSchedule;

public class TripScheduleMapper {

    public static TripScheduleDto toTripScheduleDto(TripSchedule tripSchedule) {
        Trip tripDetails = tripSchedule.getTripDetail();
        return new TripScheduleDto()
                .setId(tripSchedule.getId())
                .setTripId(tripDetails.getId())
                .setBusCode(tripDetails.getBus().getCode())
                .setAvailableSeats(tripSchedule.getAvailableSeats())
                .setFare(tripDetails.getFare())
                .setJourneyTime(tripDetails.getJourneyTime())
                .setSourceStop(tripDetails.getSourceStop().getName())
                .setDestinationStop(tripDetails.getDestStop().getName());
    }
}
