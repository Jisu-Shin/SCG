package com.example.SCG.client;

import com.example.SCG.web.dto.CustListResponseDto;
import com.example.SCG.web.dto.CustSaveRequestDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class CustServiceClient {
    private final WebClient webClient;

    public CustServiceClient(@Qualifier("custWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<CustListResponseDto> getCust(Long custId) {
        return webClient.get()
                .uri("/api/custs/{id}", custId)
                .retrieve()
                .bodyToMono(CustListResponseDto.class);
    }

    public Mono<List<CustListResponseDto>> getCustAll() {
        return webClient.get()
                .uri("/api/custs")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CustListResponseDto>>() {});
    }

    public Mono<Long> createCust(CustSaveRequestDto requestDto) {
        return webClient.post()
                .uri("/api/custs")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(Long.class);
    }

}
