package com.example.SCG.config;

import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${service.sms.url}")
    private String smsUrl;

    @Value("${service.cust.url}")
    private String custUrl;

    @Bean
    @Qualifier("smsWebClient")
    public WebClient smsWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(smsUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    @Qualifier("custWebClient")
    public WebClient custWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(custUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
