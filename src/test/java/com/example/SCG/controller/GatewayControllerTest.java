package com.example.SCG.controller;

import com.example.SCG.client.CustServiceClient;
import com.example.SCG.client.SmsServiceClient;
import com.example.SCG.dto.CustListResponseDto;
import com.example.SCG.dto.SmsWebRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import(GatewayControllerTest.MockConfig.class)
class GatewayControllerTest {

    @Autowired
    private GatewayController gatewayController;

    @Autowired
    private CustServiceClient custServiceClient;

    @Autowired
    private SmsServiceClient smsServiceClient;

    @Test
    public void 서비스_오케스트레이션() throws Exception {
        //given
        List<Long> custIdList = List.of(1L);
        for (Long i : custIdList){
            System.out.println("i = " + i);
        }
        SmsWebRequestDto requestDto = new SmsWebRequestDto(custIdList, "202504240800",1L,1L);

        List<CustListResponseDto> custInfoList = List.of(new CustListResponseDto(1L,"홍길동", "01012345678", "ALL_ALLOW"));
        for (CustListResponseDto i : custInfoList){
            System.out.println("i = " + i.getPhoneNumber());
        }

        Mono<List<CustListResponseDto>> mono = Mono.just(custInfoList);

        //when
        when(custServiceClient.findByIdList(custIdList))
                .thenReturn(mono);
        when(smsServiceClient.sendSms(any())).thenReturn(Mono.just(true));

        Boolean result = gatewayController.getCustInfoAndSendSms(requestDto).block();

        //then
        assertTrue(result);
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