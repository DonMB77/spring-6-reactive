package com.drifter.spring_6_reactive.services;

import com.drifter.spring_6_reactive.domain.BeerDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {

    Mono<BeerDTO> getBeerById(Integer beerId);

    Flux<BeerDTO> listBeers();
}
