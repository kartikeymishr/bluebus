package com.kartikeymishr.bluebusbackend.dto.mapper;

import com.kartikeymishr.bluebusbackend.dto.model.bus.TripDto;
import com.kartikeymishr.bluebusbackend.model.bus.Trip;

public class TripMapper {

    public static TripDto toTripDto(Trip trip) {
        return new TripDto()
                .setId(trip.getId())
                .setAgencyCode(trip.getAgency().getCode())
                .setSourceStopCode(trip.getSourceStop().getCode())
                .setSourceStopName(trip.getSourceStop().getName())
                .setDestinationStopCode(trip.getDestStop().getCode())
                .setDestinationStopName(trip.getDestStop().getName())
                .setBusCode(trip.getBus().getCode())
                .setJourneyTime(trip.getJourneyTime())
                .setFare(trip.getFare());
    }
}
