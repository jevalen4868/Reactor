package com.j01.reactor.config;

import com.j01.reactor.model.Taco;
import com.j01.reactor.repo.TacoRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Objects;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Data
public class RouterFunctionConfig {
    private final TacoRepo tr;
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
