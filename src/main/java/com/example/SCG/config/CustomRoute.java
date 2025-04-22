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
                .route("sms-service", r -> r.path("/api/sms/**")
                        .filters(f->f.filter(l1Filter.apply(new L1Filter.Config(true,true)))
                        )
                        .uri("http://sms-service:8080"))

                .route("sms-template-service", r -> r.path("/api/smsTemplates/**","/api/templateVariables/**")
                        .uri("http://sms-service:8080"))

                .route("cust-service", r-> r.path("/api/custs/**")
                        .uri("http://cust-service:8080"))

                .route("booking-service", r->r.path("/api/bookings/**","/api/items/**")
                        .uri("http://booking-service:8080"))

                .route("view-service", r->r.path("/view/**")
                        .filters(f->f.rewritePath("/view/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8084"))

                .route("view-static", r->r.path("/css/**","/js/**","/assets/**")
                        .uri("http://localhost:8084"))

                .build();
    }

}
