package com.j01.reactor;

import com.j01.reactor.controller.TacoController;
import com.j01.reactor.model.Ingredient;
import com.j01.reactor.model.Taco;
import com.j01.reactor.repo.TacoRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TacoControllerTest {

    @Autowired
    WebTestClient wtc;

    @MockBean
    TacoRepo tr;

    @Test
    @WithMockUser(roles = "USER")
    public void shouldReturnRecentTacos() {
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
        when(tr.findAll()).thenReturn(tacoFlux);

        wtc.get().uri("/api/tacos?recent")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Taco.class)
                .contains(Arrays.copyOf(tacos, 12));

        wtc.get().uri("/api/tacos?recent")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
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
    @WithMockUser(roles = "USER")
    public void shouldSaveATaco() {
        TacoRepo tacoRepo = Mockito.mock(
                TacoRepo.class);

        Mono<Taco> unsavedTacoMono = Mono.just(testTaco(1L));
        Taco savedTaco = testTaco(1L);
        Flux<Taco> savedTacoMono = Flux.just(savedTaco);

        when(tacoRepo.saveAll(any(Mono.class))).thenReturn(savedTacoMono);

        wtc.post()
                .uri("/api/tacos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
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
