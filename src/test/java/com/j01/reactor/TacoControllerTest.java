package com.j01.reactor;

import com.j01.reactor.config.RouterFunctionConfig;
import com.j01.reactor.model.Ingredient;
import com.j01.reactor.model.Taco;
import com.j01.reactor.repo.TacoRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TacoControllerTest {
    private TacoRepo tr;
    private Taco[] tacos;
    @BeforeAll
    public void beforeAll() {
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
        Mockito.when(tr.findAll()).thenReturn(tacoFlux);
    }

    @Test
    public void shouldReturnRecentTacos() {
        WebTestClient wtc = WebTestClient.bindToController(new RouterFunctionConfig(this.tr)).build();
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

    private Taco testTaco(Long number) {
        Taco taco = new Taco();
        taco.setId(number != null ? number.toString() : "TESTID");
        taco.setName("Taco " + number);
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("INGA", "Ingredient A", Ingredient.Type.WRAP));
        ingredients.add(new Ingredient("INGB", "Ingredient B", Ingredient.Type.PROTEIN));
        taco.setIngredients(ingredients);
        return taco;
    }
}
