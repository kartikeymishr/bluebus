package com.kartikeymishr.bluebusbackend.repository.bus;

import com.kartikeymishr.bluebusbackend.model.bus.Agency;
import com.kartikeymishr.bluebusbackend.model.bus.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

    Bus findByCode(String busCode);

    Bus findByCodeAndAgency(String busCode, Agency agency);
}
