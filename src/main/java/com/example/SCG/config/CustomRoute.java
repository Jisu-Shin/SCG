package com.example.SCG.config;

import com.example.SCG.component.L1Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class CustomRoute {

    private final L1Filter l1Filter;

    @Bean
    public RouteLocator cRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("jisutudy", r -> r.path("/sms/**")
                        .filters(f->f.filter(l1Filter.apply(new L1Filter.Config(true,true)))
                        )
                        .uri("http://localhost:8081"))
                .route("ms2", r -> r.path("/ms2/**")
                        .uri("http://localhost:8082"))
                .route("cust-service", r-> r.path("/api/custs/**")
                        .uri("http://localhost:8083"))
                .build();
    }

}
