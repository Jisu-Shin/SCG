package com.example.SCG.client;

import com.example.SCG.web.dto.CustSaveRequestDto;
import com.example.SCG.web.dto.SmsTemplateRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SmsServiceClientTest {

    @Test
    void 고객등록() {
        // given
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8081") // 로컬에 띄운 cust-service 주소
                .build();


        SmsServiceClient smsServiceClient = new SmsServiceClient(webClient);

//        CustSaveRequestDto requestDto = CustSaveRequestDto.builder()
//                .name("홍길동")
//                .phoneNumber("01012345678")
//                .smsConsentType("ALL_ALLOW")
//                .build();


        SmsTemplateRequestDto requestDto = new SmsTemplateRequestDto("테스트중","INFORMAITONAL");

        // when
        Mono<Long> resultMono = smsServiceClient.createSmsTemplate(requestDto);
        Long smsTmpltId = resultMono.block();

        // then
        assertThat(smsTmpltId).isNotNull();
        System.out.println("생성된 sms템플릿 ID: " + smsTmpltId);
    }
}