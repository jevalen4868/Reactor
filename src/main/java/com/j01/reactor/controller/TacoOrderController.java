package com.j01.reactor.controller;

import com.j01.reactor.model.TacoOrder;
import com.j01.reactor.repo.TacoOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TacoOrderController {
    private final TacoOrderRepo tor;
    public Mono<ServerResponse> getAllOrders(ServerRequest req) {
        return ServerResponse.ok().body(tor.findAll(), TacoOrder.class);
    }
}
