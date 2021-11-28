package com.j01.reactor.config;

import com.j01.reactor.controller.TacoController;
import com.j01.reactor.controller.TacoOrderController;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import java.util.Objects;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Data
public class RouterFunctionConfig {
    private TacoController tc;
    private TacoOrderController toc;
    @Bean
    public RouterFunction<?> routerFunction() {
        return route(GET("/api/tacos").and(queryParam("recent", Objects::nonNull)), tc::recents)
                .andRoute(POST("/api/tacos"), tc::postTaco);
    }
}
