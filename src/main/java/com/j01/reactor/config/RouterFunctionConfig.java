package com.j01.reactor.config;

import com.j01.reactor.controller.TacoController;
import com.j01.reactor.controller.TacoOrderController;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterFunctionConfig {
    private final TacoController tc;
    private final TacoOrderController toc;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return route(
                GET("/api/tacos")
                        .and(contentType(APPLICATION_JSON))
                        .and(queryParam("recent", Objects::nonNull)), tc::recents)
                .andRoute(POST("/api/tacos")
                        .and(contentType(APPLICATION_JSON))
                        .and(accept(APPLICATION_JSON)), tc::postTaco)
                .andRoute(GET("/api/orders")
                        .and(contentType(APPLICATION_JSON)), toc::getAllOrders);
    }
}
