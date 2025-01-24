package com.drifter.spring_6_reactive.services;

import com.drifter.spring_6_reactive.domain.Beer;
import com.drifter.spring_6_reactive.domain.BeerDTO;
import com.drifter.spring_6_reactive.mappers.BeerMapper;
import com.drifter.spring_6_reactive.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public Mono<BeerDTO> getBeerById(Integer beerId) {
        return beerRepository.findById(beerId).map(beerMapper::beerToBeerDto);
    }

    @Override
    public Flux<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .map(beerMapper::beerToBeerDto);
    }
}
