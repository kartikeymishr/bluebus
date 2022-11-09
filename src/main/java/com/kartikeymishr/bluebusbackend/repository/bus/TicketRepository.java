package com.kartikeymishr.bluebusbackend.repository.bus;

import com.kartikeymishr.bluebusbackend.model.bus.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
