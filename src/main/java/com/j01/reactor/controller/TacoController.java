package com.j01.reactor.controller;

import com.j01.reactor.model.Taco;
import com.j01.reactor.repo.TacoRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
public class TacoController {
    private final TacoRepo tr;
    /*
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
*/
    public Mono<ServerResponse> recents(ServerRequest serverRequest) {
        log.info("HELLO_00");
        return ServerResponse.ok().body(tr.findAll().take(12), Taco.class);
    }

    public Mono<ServerResponse> postTaco(ServerRequest req) {
        log.info("HELLO_01");
        return req.bodyToMono(Taco.class)
                .flatMap(tr::save)
                .flatMap((Taco savedTaco) -> {
                    return ServerResponse
                            .created(URI.create("http://localhost:8080/api/tacos/" + savedTaco.getId()))
                            .body(savedTaco, Taco.class);
                });
    }
}
