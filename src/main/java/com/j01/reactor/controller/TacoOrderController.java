package com.j01.reactor.controller;

import com.j01.reactor.repo.TacoOrderRepo;
import lombok.Data;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
public class TacoOrderController {
    private final TacoOrderRepo tor;

}
