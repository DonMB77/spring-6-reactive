package com.drifter.spring_6_reactive.repositories;

import com.drifter.spring_6_reactive.domain.Beer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BeerRepository extends ReactiveCrudRepository<Beer, Integer> {

}
