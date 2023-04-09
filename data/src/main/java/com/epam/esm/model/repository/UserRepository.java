package com.epam.esm.model.repository;

import com.epam.esm.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @code UserRepository} describes operations with User entity extending JPA repository to work with database tables.
*/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
