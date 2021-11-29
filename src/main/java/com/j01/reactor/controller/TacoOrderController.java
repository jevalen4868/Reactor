package com.j01.reactor.controller;

import com.j01.reactor.model.TacoOrder;
import com.j01.reactor.repo.TacoOrderRepo;
import lombok.Data;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Data
public class TacoOrderController {
    private final TacoOrderRepo tor;
    public Flux<TacoOrder> getAllOrders() {
        return tor.findAll();
    }
}
