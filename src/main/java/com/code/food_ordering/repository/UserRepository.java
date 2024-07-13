package com.code.food_ordering.repository;

import com.code.food_ordering.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * @return User found with the email provided
     */
    User findByEmail(String email);
}
