package com.example.SCG.client;

import com.example.SCG.web.dto.SmsFindListResponseDto;
import com.example.SCG.web.dto.SmsTemplateListResponseDto;
import com.example.SCG.web.dto.SmsTemplateRequestDto;
import com.example.SCG.web.dto.TemplateVariableDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class SmsServiceClient {
    private final WebClient webClient;

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

    public Mono<Long> createSmsTemplate(SmsTemplateRequestDto requestDto) {
        return webClient.post()
                .uri("/api/smsTemplates")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(Long.class);
    }

    public Mono<Long> createTemplateVariable(TemplateVariableDto requestDto) {
        return webClient.post()
                .uri("/api/templateVariables")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(Long.class);
    }

    public Mono<List<TemplateVariableDto>> getTmpltVarList() {
        return webClient.get()
                .uri("/api/templateVariables")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TemplateVariableDto>>() {});
    }

}
