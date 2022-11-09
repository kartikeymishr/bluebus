package com.kartikeymishr.bluebusbackend.repository.bus;

import com.kartikeymishr.bluebusbackend.model.bus.Agency;
import com.kartikeymishr.bluebusbackend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {

    Agency findByCode(String agencyCode);

    Agency findByOwner(User owner);

    Agency findByName(String name);
}
