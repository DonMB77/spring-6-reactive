package com.drifter.spring_6_reactive.controllers;

import com.drifter.spring_6_reactive.domain.Beer;
import com.drifter.spring_6_reactive.domain.BeerDTO;
import com.drifter.spring_6_reactive.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class BeerController {

    public static final String BEER_PATH = "api/v2/beer";
    public static final String BEER_PATH_WITH_ID = BEER_PATH + "/{beerId}";
    public static final String LOCALHOST_BEER_PATH = "http://localhost:8080/" + BEER_PATH;
    public static final String LOCALHOST_BEER_PATH_WITH_ID = "http://localhost:8080/" + BEER_PATH_WITH_ID;
    private final BeerService beerService;

    @DeleteMapping(BEER_PATH_WITH_ID)
    Mono<ResponseEntity<Void>> deleteById(@PathVariable("beerId") Integer beerId) {
        return beerService.getBeerById(beerId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(beerDto -> beerService.deleteById(beerDto.getId()))
                .thenReturn(ResponseEntity.noContent().build());
    }

    @PatchMapping(BEER_PATH_WITH_ID)
    Mono<ResponseEntity<Void>> patchBeer(@PathVariable("beerId") Integer beerId, @Validated @RequestBody BeerDTO beerDTO) {
        return beerService.patchBeer(beerId, beerDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(updatedDto -> ResponseEntity.ok().build());
    }

    @PutMapping(BEER_PATH_WITH_ID)
    Mono<ResponseEntity<Void>> updateExistingBeer(@PathVariable("beerId") Integer beerId,
                                                  @Validated @RequestBody BeerDTO beerDTO){
        return beerService.updateExistingBeer(beerId, beerDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(savedDto -> ResponseEntity.noContent().build());
    }

    @PostMapping(BEER_PATH)
    Mono<ResponseEntity<Void>> createNewBeer(@Validated @RequestBody BeerDTO beerDTO) {

        return beerService.saveNewBeer(beerDTO).
                map(savedDto -> ResponseEntity.created(UriComponentsBuilder
                        .fromHttpUrl("http://localhost:8080/" + BEER_PATH + "/" + savedDto.getId())
                        .build().toUri()).build());
    }

    @GetMapping(BEER_PATH_WITH_ID)
    Mono<BeerDTO> getBeerById(@PathVariable("beerId") Integer beerId) {
        return beerService.getBeerById(beerId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @GetMapping(BEER_PATH)
    Flux<BeerDTO> listBeers() {
        return beerService.listBeers();
    }
}
