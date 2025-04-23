package com.example.SCG.controller;

import com.example.SCG.client.CustServiceClient;
import com.example.SCG.client.SmsServiceClient;
import com.example.SCG.dto.CustInfo;
import com.example.SCG.dto.SmsSendRequestDto;
import com.example.SCG.dto.SmsWebRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GatewayController {

    private final CustServiceClient custServiceClient;
    private final SmsServiceClient smsServiceClient;

    // 예매 내역 때 고객명도 같이 가져와야함
//    @GetMapping("/send-sms-and-notify")
//    public String sendSmsAndNotify(@RequestParam Long custId) {
//        return custServiceClient.getCust(custId)
//                .flatMap( cust -> {
//                    String phoneNumber = cust.getPhoneNumber();
//                    return smsServiceClient.send();
//                })
//                .then(Mono.just("SMS 발송완료"));
//    }

    // 문자 내역 조회 때 고객명도 같이 가져와야함

    @PostMapping("/sms/send-by-cust-ids")
    public Mono<Boolean> getCustInfoAndSendSms(@RequestBody SmsWebRequestDto requestDto) {
        return custServiceClient.findByIdList(requestDto.getCustIdList())
                .flatMap(custList -> {
                    List<CustInfo> custInfoList = custList.stream()
                            .map(CustInfo::new)
                            .collect(Collectors.toList());

                    SmsSendRequestDto smsSendRequestDto = requestDto.toSmsSendRequestDto();
                    smsSendRequestDto.setCustInfoList(custInfoList);
                    return smsServiceClient.sendSms(smsSendRequestDto);
                });
    }
}