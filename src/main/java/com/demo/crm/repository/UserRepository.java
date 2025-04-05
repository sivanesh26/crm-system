package com.demo.crm.repository;

import com.demo.crm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository  // Marks this interface as a Spring Data repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Method to find a user by email, returning an Optional<User>
	
    Optional<User> findByEmail(String email);
}


