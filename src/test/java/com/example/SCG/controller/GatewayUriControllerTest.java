package com.example.SCG.controller;

import com.example.SCG.client.CustServiceClient;
import com.example.SCG.client.SmsServiceClient;
import com.example.SCG.dto.CustListResponseDto;
import com.example.SCG.dto.SmsWebRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = GatewayController.class)
@Import(GatewayUriControllerTest.MockConfig.class)
class GatewayUriControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CustServiceClient custServiceClient;

    @Autowired
    private SmsServiceClient smsServiceClient;

    @Test
    public void 서비스_오케스트레이션() throws Exception {
        // Given
        List<Long> custIdList = List.of(1L);
        SmsWebRequestDto requestDto = new SmsWebRequestDto(custIdList, "202504240800", 1L, 1L);
        List<CustListResponseDto> custInfoList = List.of(new CustListResponseDto(1L,"홍길동", "01012345678", "ALL_ALLOW"));
        Mono<List<CustListResponseDto>> mono = Mono.just(custInfoList);

        when(custServiceClient.findByIdList(custIdList)).thenReturn(mono);
        when(smsServiceClient.sendSms(any())).thenReturn(Mono.just(true));

        // When + Then
        webTestClient.post()
                .uri("/sms/send-by-cust-ids")
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Boolean.class)
                .isEqualTo(true);
    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        public CustServiceClient custServiceClient() {
            return mock(CustServiceClient.class);
        }

        @Bean
        public SmsServiceClient smsServiceClient() {
            return mock(SmsServiceClient.class);
        }

        @Bean
        public GatewayController gatewayController(CustServiceClient custServiceClient, SmsServiceClient smsServiceClient) {
            return new GatewayController(custServiceClient, smsServiceClient);
        }
    }

}