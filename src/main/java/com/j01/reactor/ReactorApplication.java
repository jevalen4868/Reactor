package com.j01.reactor;

import com.j01.reactor.exception.UnknownIngredientException;
import com.j01.reactor.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@SpringBootApplication
public class ReactorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactorApplication.class, args);

        Mono<Ingredient> ing = WebClient.create()
                .get()
                .uri("http://localhost:8080/ingredients/{id}", 0)
                .retrieve()
                .bodyToMono(Ingredient.class);
        ing.subscribe(System.out::println);

        Flux<Ingredient> ings = WebClient.create()
                .get()
                .uri("http://localhost:8080/ingredients")
                .retrieve()
                .bodyToFlux(Ingredient.class);

        ings.subscribe(System.out::println);

        Flux<Ingredient> ingredients = WebClient.create()
                .get()
                .uri("/ingredients")
                .retrieve()
                .bodyToFlux(Ingredient.class);

        ingredients
                .timeout(Duration.ofSeconds(1))
                .subscribe(
                        System.out::println,
                        e -> {
                            // handle timeout error
                        });

        Ingredient ingredient = new Ingredient();
        ingredient.setId(0L);
        ingredient.setSlug("INGB");
        ingredient.setName("Ingredient B");
        ingredient.setType(Ingredient.Type.PROTEIN);

        Mono<Ingredient> ingredientMono = Mono.just(ingredient);

        Mono<Ingredient> result = WebClient.create()
                .post()
                .uri("/ingredients")
                .body(ingredientMono, Ingredient.class)
                .retrieve()
                .bodyToMono(Ingredient.class);

        result.subscribe(System.out::println);

        Mono<Ingredient> result1 = WebClient.create()
                .post()
                .uri("/ingredients")
                .bodyValue(ingredient)
                .retrieve()
                .bodyToMono(Ingredient.class);

        result1.subscribe(System.out::println);

        Mono<Void> result2 = WebClient.create()
                .put()
                .uri("/ingredients/{id}", ingredient.getId())
                .bodyValue(ingredient)
                .retrieve()
                .bodyToMono(Void.class);

        result2.subscribe();

        Mono<Void> result3 = WebClient.create()
                .delete()
                .uri("/ingredients/{id}", 0)
                .retrieve()
                .bodyToMono(Void.class);

        result3.subscribe();

        Mono<Ingredient> ingredientMono1 = WebClient.create()
                .get()
                .uri("http://localhost:8080/ingredients/{id}", 0)
                .retrieve()
                .bodyToMono(Ingredient.class);

        ingredientMono1.subscribe(
                i -> {
                    // handle the ingredient data
                },
                error-> {
                    // deal with the error
                });

        Mono<Ingredient> ingredientMonoErr = WebClient.create()
                .get()
                .uri("http://localhost:8080/ingredients/{id}", 0)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> Mono.just(new UnknownIngredientException()))
                .bodyToMono(Ingredient.class);

        Mono<Ingredient> ingredientMono404 = WebClient.create()
                .get()
                .uri("http://localhost:8080/ingredients/{id}", 0)
                .retrieve()
                .onStatus(status -> status == HttpStatus.NOT_FOUND,
                        response -> Mono.just(new UnknownIngredientException()))
                .bodyToMono(Ingredient.class);

        Mono<Ingredient> ingredientMono2 =  WebClient.create()
                .get()
                .uri("http://localhost:8080/ingredients/{id}", 0)
                .exchangeToMono(cr -> cr.bodyToMono(Ingredient.class));

        Mono<Ingredient> ingredientMono3 = WebClient.create()
                .get()
                .uri("http://localhost:8080/ingredients/{id}", 0)
                .exchangeToMono(cr -> {
                    if (cr.headers().header("X_UNAVAILABLE").contains("true")) {
                        return Mono.empty();
                    }
                    return Mono.just(cr);
                })
                .flatMap(cr -> cr.bodyToMono(Ingredient.class));
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create("http://localhost:8080");
    }


    public Mono<Ingredient> getIngredientById(String ingredientId) {
        Mono<Ingredient> ingredient = WebClient.create("http://localhost:8080")
                .get()
                .uri("/ingredients/{id}", ingredientId)
                .retrieve()
                .bodyToMono(Ingredient.class);
        return ingredient;
    }
}
