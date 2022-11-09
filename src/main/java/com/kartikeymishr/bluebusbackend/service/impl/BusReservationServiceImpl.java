package com.kartikeymishr.bluebusbackend.service.impl;

import com.kartikeymishr.bluebusbackend.dto.mapper.TicketMapper;
import com.kartikeymishr.bluebusbackend.dto.mapper.TripMapper;
import com.kartikeymishr.bluebusbackend.dto.mapper.TripScheduleMapper;
import com.kartikeymishr.bluebusbackend.dto.model.bus.*;
import com.kartikeymishr.bluebusbackend.dto.model.user.UserDto;
import com.kartikeymishr.bluebusbackend.exception.BluebusException;
import com.kartikeymishr.bluebusbackend.exception.EntityType;
import com.kartikeymishr.bluebusbackend.exception.ExceptionType;
import com.kartikeymishr.bluebusbackend.model.bus.*;
import com.kartikeymishr.bluebusbackend.model.user.User;
import com.kartikeymishr.bluebusbackend.repository.bus.*;
import com.kartikeymishr.bluebusbackend.repository.user.UserRepository;
import com.kartikeymishr.bluebusbackend.service.BusReservationService;
import com.kartikeymishr.bluebusbackend.util.RandomStringUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static com.kartikeymishr.bluebusbackend.exception.EntityType.*;
import static com.kartikeymishr.bluebusbackend.exception.ExceptionType.*;

@Service
public class BusReservationServiceImpl implements BusReservationService {

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripScheduleRepository tripScheduleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public StopDto getStopByCode(String stopCode) {
        Optional<Stop> stop = Optional.ofNullable(stopRepository.findByCode(stopCode));
        if (stop.isPresent()) {
            return modelMapper.map(stop.get(), StopDto.class);
        }

        throw exception(STOP, ENTITY_NOT_FOUND, stopCode);
    }

    @Override
    public AgencyDto getAgency(UserDto userDto) {
        User user = getUser(userDto.getEmail());
        Optional<Agency> agency = Optional.ofNullable(agencyRepository.findByOwner(user));
        if (agency.isPresent()) {
            return modelMapper.map(agency.get(), AgencyDto.class);
        }

        throw exceptionWithId(AGENCY, ENTITY_NOT_FOUND, 2, user.getEmail());
    }

    @Override
    public AgencyDto addAgency(AgencyDto agencyDto) {
        User admin = getUser(agencyDto.getOwner().getEmail());
        Optional<Agency> agency = Optional.ofNullable(agencyRepository.findByName(agencyDto.getName()));
        if (agency.isEmpty()) {
            Agency agencyModel = new Agency()
                    .setName(agencyDto.getName())
                    .setDetails(agencyDto.getDetails())
                    .setCode(RandomStringUtil.getAlphaNumericString(8, agencyDto.getName()))
                    .setOwner(admin);
            agencyRepository.save(agencyModel);

            return modelMapper.map(agencyModel, AgencyDto.class);
        }

        throw exception(AGENCY, DUPLICATE_ENTITY, agencyDto.getName());

    }

    @Override
    @Transactional
    public AgencyDto updateAgency(AgencyDto agencyDto, BusDto busDto) {
        Agency agency = getAgency(agencyDto.getCode());
        if (busDto != null) {
            Optional<Bus> bus = Optional.ofNullable(busRepository.findByCodeAndAgency(busDto.getCode(), agency));
            if (bus.isEmpty()) {
                Bus busModel = new Bus()
                        .setAgency(agency)
                        .setCode(busDto.getCode())
                        .setCapacity(busDto.getCapacity())
                        .setMake(busDto.getMake());
                busRepository.save(busModel);

                if (agency.getBuses() == null) {
                    agency.setBuses(new HashSet<>());
                }
                agency.getBuses().add(busModel);

                return modelMapper.map(agencyRepository.save(agency), AgencyDto.class);
            }

            throw exceptionWithId(BUS, DUPLICATE_ENTITY, 2, busDto.getCode(), agencyDto.getCode());
        } else {
            agency.setName(agencyDto.getName())
                    .setDetails(agencyDto.getDetails());

            return modelMapper.map(agencyRepository.save(agency), AgencyDto.class);
        }

    }

    @Override
    public TripDto getTripById(Long tripID) {
        Optional<Trip> trip = tripRepository.findById(tripID);
        if (trip.isPresent()) {
            return TripMapper.toTripDto(trip.get());
        }

        throw exception(TRIP, ENTITY_NOT_FOUND, tripID.toString());
    }

    @Override
    public List<TripDto> addTrip(TripDto tripDto) {
        Stop sourceStop = getStop(tripDto.getSourceStopCode());
        Stop destinationStop = getStop(tripDto.getDestinationStopCode());

        if (!sourceStop.getCode().equalsIgnoreCase(destinationStop.getCode())) {
            Agency agency = getAgency(tripDto.getAgencyCode());
            Bus bus = getBus(tripDto.getBusCode());
            //Each new trip creation results in a to and a fro trip
            List<TripDto> trips = new ArrayList<>(2);
            Trip toTrip = new Trip()
                    .setSourceStop(sourceStop)
                    .setDestStop(destinationStop)
                    .setAgency(agency)
                    .setBus(bus)
                    .setJourneyTime(tripDto.getJourneyTime())
                    .setFare(tripDto.getFare());
            trips.add(TripMapper.toTripDto(tripRepository.save(toTrip)));

            Trip froTrip = new Trip()
                    .setSourceStop(destinationStop)
                    .setDestStop(sourceStop)
                    .setAgency(agency)
                    .setBus(bus)
                    .setJourneyTime(tripDto.getJourneyTime())
                    .setFare(tripDto.getFare());
            trips.add(TripMapper.toTripDto(tripRepository.save(froTrip)));

            return trips;

        }

        throw exception(TRIP, ENTITY_EXCEPTION, "");

    }

    @Override
    public List<TripDto> getAgencyTrips(String agencyCode) {
        Agency agency = getAgency(agencyCode);
        List<Trip> agencyTrips = tripRepository.findByAgency(agency);
        if (!agencyTrips.isEmpty()) {
            return agencyTrips
                    .stream()
                    .map(TripMapper::toTripDto)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    @Override
    public List<TripDto> getAvailableTripsBetweenStops(String sourceStopCode, String destinationStopCode) {
        List<Trip> availableTrips = findTripsBetweenStops(sourceStopCode, destinationStopCode);
        if (!availableTrips.isEmpty()) {
            return availableTrips
                    .stream()
                    .map(TripMapper::toTripDto)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    @Override
    public List<TripScheduleDto> getAvailableTripSchedules(String sourceStopCode, String destinationStopCode, String tripDate) {
        List<Trip> availableTrips = findTripsBetweenStops(sourceStopCode, destinationStopCode);
        if (!availableTrips.isEmpty()) {
            return availableTrips
                    .stream()
                    .map(trip -> getTripSchedule(TripMapper.toTripDto(trip), tripDate, true))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    @Override
    public TripScheduleDto getTripSchedule(TripDto tripDto, String tripDate, boolean createSchedForTrip) {
        Optional<Trip> trip = tripRepository.findById(tripDto.getId());
        if (trip.isPresent()) {
            Optional<TripSchedule> tripSchedule = Optional.ofNullable(tripScheduleRepository.findByTripDetailAndTripDate(trip.get(), tripDate));
            if (tripSchedule.isPresent()) {
                return TripScheduleMapper.toTripScheduleDto(tripSchedule.get());
            } else {
                if (createSchedForTrip) { //create the schedule
                    TripSchedule tripSchedule1 = new TripSchedule()
                            .setTripDetail(trip.get())
                            .setTripDate(tripDate)
                            .setAvailableSeats(trip.get().getBus().getCapacity());

                    return TripScheduleMapper.toTripScheduleDto(tripScheduleRepository.save(tripSchedule1));
                } else {
                    throw exceptionWithId(TRIP, ENTITY_NOT_FOUND, 2, tripDto.getId().toString(), tripDate);
                }
            }
        }

        throw exception(TRIP, ENTITY_NOT_FOUND, tripDto.getId().toString());
    }

    @Override
    @Transactional
    public TicketDto bookTicket(TripScheduleDto tripScheduleDto, UserDto passenger) {
        User user = getUser(passenger.getEmail());
        Optional<TripSchedule> tripSchedule = tripScheduleRepository.findById(tripScheduleDto.getId());
        if (tripSchedule.isPresent()) {
            Ticket ticket = new Ticket()
                    .setCancellable(false)
                    .setJourneyDate(tripSchedule.get().getTripDate())
                    .setPassenger(user)
                    .setTripSchedule(tripSchedule.get())
                    .setSeatNumber(tripSchedule.get().getTripDetail().getBus().getCapacity() - tripSchedule.get().getAvailableSeats());
            ticketRepository.save(ticket);
            tripSchedule.get().setAvailableSeats(tripSchedule.get().getAvailableSeats() - 1); // reduce availability by 1
            tripScheduleRepository.save(tripSchedule.get()); // update schedule

            return TicketMapper.toTicketDto(ticket);
        }

        throw exceptionWithId(TRIP, ENTITY_NOT_FOUND, 2, tripScheduleDto.getTripId().toString(), tripScheduleDto.getTripDate());
    }

    /**
     * Search for all Trips between src and dest stops
     *
     * @param sourceStopCode
     * @param destinationStopCode
     * @return
     */
    private List<Trip> findTripsBetweenStops(String sourceStopCode, String destinationStopCode) {
        Stop sourceStop = getStop(sourceStopCode);
        Stop destStop = getStop(destinationStopCode);

        List<Trip> availableTrips = tripRepository.findAllBySourceStopAndDestStop(sourceStop, destStop);
        if (!availableTrips.isEmpty()) {
            return availableTrips;
        }

        return Collections.emptyList();

    }

    /**
     * Fetch user from UserDto
     *
     * @param email
     * @return
     */
    private User getUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw exception(USER, ENTITY_NOT_FOUND, email);
        }

        return user;
    }

    /**
     * Fetch Stop from stopCode
     *
     * @param stopCode
     * @return
     */
    private Stop getStop(String stopCode) {
        Stop stop = stopRepository.findByCode(stopCode);
        if (stop == null) {
            throw exception(STOP, ENTITY_NOT_FOUND, stopCode);
        }

        return stop;
    }

    /**
     * Fetch Bus from busCode, since it is unique we don't have issues of finding duplicate Buses
     *
     * @param busCode
     * @return
     */
    private Bus getBus(String busCode) {
        Bus bus = busRepository.findByCode(busCode);
        if (bus == null) {
            throw exception(BUS, ENTITY_NOT_FOUND, busCode);
        }

        return bus;
    }

    /**
     * Fetch Agency from agencyCode
     *
     * @param agencyCode
     * @return
     */
    private Agency getAgency(String agencyCode) {
        Agency agency = agencyRepository.findByCode(agencyCode);
        if (agency == null) {
            throw exception(AGENCY, ENTITY_NOT_FOUND, agencyCode);
        }

        return agency;
    }

    /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return BluebusException.throwException(entityType, exceptionType, args);
    }

    /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    private RuntimeException exceptionWithId(EntityType entityType, ExceptionType exceptionType, Integer id, String... args) {
        return BluebusException.throwExceptionWithId(entityType, exceptionType, id, args);
    }
}
