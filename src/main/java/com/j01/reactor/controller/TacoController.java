package com.j01.reactor.controller;

import com.j01.reactor.model.Taco;
import com.j01.reactor.repo.TacoRepo;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
}
