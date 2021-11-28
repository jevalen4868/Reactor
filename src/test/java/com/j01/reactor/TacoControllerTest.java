package com.j01.reactor;

import com.j01.reactor.controller.TacoController;
import com.j01.reactor.model.Ingredient;
import com.j01.reactor.model.Taco;
import com.j01.reactor.repo.TacoRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TacoControllerTest {
    @Test
    public void shouldReturnRecentTacos() {
        TacoRepo tr;
        Taco[] tacos;
        tacos = new Taco[]{
                testTaco(1L), testTaco(2L),
                testTaco(3L), testTaco(4L),
                testTaco(5L), testTaco(6L),
                testTaco(7L), testTaco(8L),
                testTaco(9L), testTaco(10L),
                testTaco(11L), testTaco(12L),
                testTaco(13L), testTaco(14L),
                testTaco(15L), testTaco(16L)};
        Flux<Taco> tacoFlux = Flux.just(tacos);
        tr = Mockito.mock(TacoRepo.class);
        when(tr.findAll()).thenReturn(tacoFlux);

        WebTestClient wtc = WebTestClient.bindToController(new TacoController(tr)).build();

        wtc.get().uri("/api/tacos?recent")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Taco.class)
                .contains(Arrays.copyOf(tacos, 12));

        wtc.get().uri("/api/tacos?recent")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$").isNotEmpty()
                .jsonPath("$[0].id").isEqualTo(tacos[0].getId().toString())
                .jsonPath("$[0].name").isEqualTo("Taco 1")
                .jsonPath("$[1].id").isEqualTo(tacos[1].getId().toString())
                .jsonPath("$[1].name").isEqualTo("Taco 2")
                .jsonPath("$[11].id").isEqualTo(tacos[11].getId().toString())
                .jsonPath("$[11].name").isEqualTo("Taco 12")
                .jsonPath("$[12]").doesNotExist();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldSaveATaco() {
        TacoRepo tacoRepo = Mockito.mock(
                TacoRepo.class);

        WebTestClient testClient = WebTestClient.bindToController(
                new TacoController(tacoRepo)).build();

        Mono<Taco> unsavedTacoMono = Mono.just(testTaco(1L));
        Taco savedTaco = testTaco(1L);
        Flux<Taco> savedTacoMono = Flux.just(savedTaco);

        when(tacoRepo.saveAll(any(Mono.class))).thenReturn(savedTacoMono);


        testClient.post()
                .uri("/api/tacos")
                .contentType(MediaType.APPLICATION_JSON)
                .body(unsavedTacoMono, Taco.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Taco.class)
                .isEqualTo(savedTaco);
    }

    private Taco testTaco(Long number) {
        Taco taco = new Taco();
        taco.setId(number);
        taco.setName("Taco " + number);
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient i1 = new Ingredient();
        i1.setId(0L);
        i1.setSlug("INGA");
        i1.setName("Ingredient A");
        i1.setType(Ingredient.Type.WRAP);
        ingredients.add(i1);
        Ingredient i2 = new Ingredient();
        i2.setId(1L);
        i2.setSlug("INGB");
        i2.setName("Ingredient B");
        i2.setType(Ingredient.Type.PROTEIN);
        ingredients.add(i1);
        ingredients.add(i2);
        ingredients.forEach(taco::addIng);

        return taco;
    }
}
