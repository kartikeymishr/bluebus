package com.kartikeymishr.bluebusbackend.repository.user;

import com.kartikeymishr.bluebusbackend.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
     User findByEmail(String email);
}
