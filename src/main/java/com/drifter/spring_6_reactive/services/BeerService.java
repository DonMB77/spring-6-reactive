package com.drifter.spring_6_reactive.services;

import com.drifter.spring_6_reactive.domain.BeerDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

public interface BeerService {

    Flux<BeerDTO> listBeers();
}
