package com.personal.specialrequestsmanagementapi.repositories;

import com.personal.specialrequestsmanagementapi.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
