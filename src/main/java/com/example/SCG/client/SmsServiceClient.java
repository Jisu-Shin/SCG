package com.example.SCG.client;

import com.example.SCG.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class SmsServiceClient {
    private final WebClient webClient;

    @Autowired
    public SmsServiceClient(@Qualifier("smsWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<SmsFindListResponseDto>> getSmsList(String startDt, String endDt) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/sms/sendList")
                        .queryParam("startDt", startDt)
                        .queryParam("endDt", endDt)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<SmsFindListResponseDto>>() {
                });
    }

    public Mono<List<SmsTemplateListResponseDto>> getSmsTemplates() {
        return webClient.get()
                .uri("/api/smsTemplates")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<SmsTemplateListResponseDto>>() {
                });
    }

    public Mono<Boolean> sendSms(SmsSendRequestDto requestDto) {
        return webClient.post()
                .uri("/api/sms/send")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(Boolean.class);
    }

    public Mono<Long> createSmsTemplate(SmsTemplateRequestDto requestDto) {
        return webClient.post()
                .uri("/api/smsTemplates")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(Long.class);
    }


}
