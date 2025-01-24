package com.drifter.spring_6_reactive.repositories;

import com.drifter.spring_6_reactive.domain.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {
}
