package com.kartikeymishr.bluebusbackend.repository.bus;

import com.kartikeymishr.bluebusbackend.model.bus.Trip;
import com.kartikeymishr.bluebusbackend.model.bus.TripSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripScheduleRepository extends JpaRepository<TripSchedule, Long> {
    TripSchedule findByTripDetailAndTripDate(Trip tripDetail, String tripDate);
}
