package com.j01.reactor.config;

import com.j01.reactor.controller.TacoController;
import com.j01.reactor.controller.TacoOrderController;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;

import java.util.Objects;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Data
public class RouterFunctionConfig {
    private final TacoController tc;
    private final TacoOrderController toc;

    @Bean
    public RouterFunction<?> routerFunction() {
        // get
        return route(GET("/api/tacos")
                .and(contentType(MediaType.APPLICATION_JSON))
                .and(queryParam("recent", Objects::nonNull))
                .and(accept(MediaType.APPLICATION_JSON)), tc::recents)
                // post
                .andRoute(POST("/api/tacos").and(contentType(MediaType.APPLICATION_JSON)), tc::postTaco);
    }
}
