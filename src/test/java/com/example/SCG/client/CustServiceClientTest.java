package com.example.SCG.client;

import com.example.SCG.dto.CustSaveRequestDto;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class CustServiceClientTest {

    private WebClient webClient;
    private MockWebServer mockWebServer;
    private CustServiceClient custServiceClient;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();

        custServiceClient = new CustServiceClient(webClient);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void 고객등록_mock() throws InterruptedException {
        // given
        CustSaveRequestDto requestDto = CustSaveRequestDto.builder()
                .name("홍길동")
                .phoneNumber("01012345678")
                .smsConsentType("ALL_ALLOW")
                .build();

        mockWebServer.enqueue(new MockResponse()
                .setBody("1")
                .addHeader("Content-type", "application/json"));

        // when
        Mono<Long> resultMono = custServiceClient.createCust(requestDto);
        Long custId = resultMono.block();

        // then
        assertThat(custId).isNotNull();
        System.out.println(custId);
        assertThat(custId).isEqualTo(1L);
    }

    @Test
    void 고객등록() {
        // given
        WebClient webClient = WebClient.builder()
                .baseUrl("http://cust-service:8080") // 로컬에 띄운 cust-service 주소
                .build();

        CustServiceClient custServiceClient = new CustServiceClient(webClient);

        CustSaveRequestDto requestDto = CustSaveRequestDto.builder()
                .name("홍길동")
                .phoneNumber("01012345678")
                .smsConsentType("ALL_ALLOW")
                .build();

        // when
        Mono<Long> resultMono = custServiceClient.createCust(requestDto);
        Long custId = resultMono.block();

        // then
        assertThat(custId).isNotNull();
        System.out.println("생성된 고객 ID: " + custId);
    }

}