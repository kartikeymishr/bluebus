package com.kartikeymishr.bluebusbackend.service;

import com.kartikeymishr.bluebusbackend.dto.model.bus.*;
import com.kartikeymishr.bluebusbackend.dto.model.user.UserDto;

import java.util.List;
import java.util.Set;

public interface BusReservationService {

    StopDto getStopByCode(String stopCode);

    AgencyDto getAgency(UserDto userDto);

    AgencyDto addAgency(AgencyDto agencyDto);

    AgencyDto updateAgency(AgencyDto agencyDto, BusDto busDto);

    TripDto getTripById(Long tripID);

    List<TripDto> addTrip(TripDto tripDto);

    List<TripDto> getAgencyTrips(String agencyCode);

    List<TripDto> getAvailableTripsBetweenStops(String sourceStopCode, String destinationStopCode);

    List<TripScheduleDto> getAvailableTripSchedules(String sourceStopCode, String destinationStopCode, String tripDate);

    TripScheduleDto getTripSchedule(TripDto tripDto, String tripDate, boolean createSchedForTrip);

    TicketDto bookTicket(TripScheduleDto tripScheduleDto, UserDto passenger);
}
