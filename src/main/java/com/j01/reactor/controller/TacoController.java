package com.j01.reactor.controller;

import com.j01.reactor.model.Taco;
import com.j01.reactor.repo.TacoRepo;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Objects;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RestController
@Data
public class TacoController {
    private final TacoRepo tr;
    @GetMapping(params = "recent")
    public Flux<Taco> recentTacos() {
        return this.tr.findAll().take(12);
    }
    @GetMapping("/{id}")
    public Mono<Taco> findById(@PathVariable("id") Long id) {
        return this.tr.findById(id);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Taco> postTaco(@RequestBody Mono<Taco> tacoMono) {
        //return this.tr.saveAll(tacoMono).next() ;
        return tacoMono.flatMap(this.tr::save);
    }

    @Bean
    public RouterFunction<?> routerFunction() {
        return route(GET("/api/tacos").and(queryParam("recent", Objects::nonNull)), this::recents)
                .andRoute(POST("/api/tacos"), this::postTaco);
    }

    public Mono<ServerResponse> recents(ServerRequest serverRequest) {
        return ServerResponse.ok().body(tr.findAll().take(12), Taco.class);
    }

    public Mono<ServerResponse> postTaco(ServerRequest req) {
        return req.bodyToMono(Taco.class)
                .flatMap(tr::save)
                .flatMap((Taco savedTaco) -> {
                    return ServerResponse
                            .created(URI.create("http://localhost:8080/api/tacos/" + savedTaco.getId()))
                            .body(savedTaco, Taco.class);
                });
    }
}
