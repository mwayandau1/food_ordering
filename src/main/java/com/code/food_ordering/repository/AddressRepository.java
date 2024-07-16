package com.code.food_ordering.repository;

import com.code.food_ordering.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
