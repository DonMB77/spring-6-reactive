package com.drifter.spring_6_reactive.controllers;

import com.drifter.spring_6_reactive.domain.Beer;
import com.drifter.spring_6_reactive.domain.BeerDTO;
import com.drifter.spring_6_reactive.repositories.BeerRepositoryTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void testPatchBeerNotFound() {
        webTestClient.patch()
                .uri(BeerController.LOCALHOST_BEER_PATH_WITH_ID, 999)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteNotFound() {
        webTestClient.delete()
                .uri(BeerController.LOCALHOST_BEER_PATH_WITH_ID, 999)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(999)
    void testDeleteBeer() {
        webTestClient.delete()
                .uri(BeerController.LOCALHOST_BEER_PATH_WITH_ID,1)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(4)
    void testUpdateBeerBadRequest() {
        Beer testBeer = BeerRepositoryTest.getTestBeer();
        testBeer.setBeerStyle("");
        webTestClient.put()
                .uri(BeerController.LOCALHOST_BEER_PATH_WITH_ID,1)
                .body(Mono.just(testBeer), BeerDTO.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testUpdateBeerNotFound() {
        webTestClient.put()
                .uri(BeerController.LOCALHOST_BEER_PATH_WITH_ID,999)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void testUpdateBeer() {
        webTestClient.put()
                .uri(BeerController.LOCALHOST_BEER_PATH_WITH_ID,1)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testCreateBeerBad() {
        Beer testBeer = BeerRepositoryTest.getTestBeer();
        testBeer.setBeerName("");

        webTestClient.post().uri(BeerController.LOCALHOST_BEER_PATH)
                .body(Mono.just(testBeer), BeerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testCreateBeer() {
        webTestClient.post().uri(BeerController.LOCALHOST_BEER_PATH)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/beer/4");
    }

    @Test
    void testBeerByIdNotFound() {
        webTestClient.get().uri(BeerController.LOCALHOST_BEER_PATH_WITH_ID, 999)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(1)
    void testBeerById() {
        webTestClient.get().uri(BeerController.LOCALHOST_BEER_PATH_WITH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody(BeerDTO.class);
    }

    @Test
    @Order(2)
    void testListBeers() {
        webTestClient.get().uri(BeerController.LOCALHOST_BEER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }
}