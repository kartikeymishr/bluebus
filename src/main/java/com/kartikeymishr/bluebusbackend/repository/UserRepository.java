package com.kartikeymishr.bluebusbackend.repository;

import com.kartikeymishr.bluebusbackend.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
     User findByEmail(String email);
}
