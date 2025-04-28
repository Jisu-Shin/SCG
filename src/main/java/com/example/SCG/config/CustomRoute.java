package com.example.SCG.config;

import com.example.SCG.component.L1Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class CustomRoute {

    private final L1Filter l1Filter;

    @Value("${service.sms.url}")
    private String smsUrl;

    @Value("${service.cust.url}")
    private String custUrl;

    @Value("${service.booking.url}")
    private String bookingUrl;

    @Value("${service.view.url}")
    private String viewUrl;

    @Bean
    public RouteLocator cRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("sms-service", r -> r.path("/api/sms/**")
                        .filters(f->f.filter(l1Filter.apply(new L1Filter.Config(true,true)))
                        )
                        .uri(smsUrl))

                .route("sms-template-service", r -> r.path("/api/smsTemplates/**","/api/templateVariables/**")
                        .uri(smsUrl))

                .route("cust-service", r-> r.path("/api/custs/**")
                        .uri(custUrl))

                .route("booking-service", r->r.path("/api/bookings/**","/api/items/**")
                        .uri(bookingUrl))

                .route("view-service", r->r.path("/view/**")
                        .filters(f->f.rewritePath("/view/(?<segment>.*)", "/${segment}"))
                        .uri(viewUrl))

                .route("view-static", r->r.path("/css/**","/js/**","/assets/**")
                        .uri(viewUrl))

                .build();
    }

}
