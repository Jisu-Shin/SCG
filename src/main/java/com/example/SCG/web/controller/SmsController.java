package com.example.SCG.web.controller;

import com.example.SCG.client.SmsServiceClient;
import com.example.SCG.web.dto.SmsFindListResponseDto;
import com.example.SCG.web.dto.SmsTemplateListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sms")
public class SmsController {

    private final SmsServiceClient smsServiceClient;
//    private final ItemService itemService;

    @GetMapping("/send")
    public String sendSms(Model model) {
        List<SmsTemplateListResponseDto> tmpltList = smsServiceClient.getSmsTemplates().block();
        model.addAttribute("templates", tmpltList);
//        model.addAttribute("items", itemService.findAll());
        return "sms-sendForm";
    }

    @GetMapping("/sendList")
    public String getSmsList(Model model) {
        LocalDate startDt = LocalDate.now().minusDays(6);
        String parseStatDt = startDt.format(DateTimeFormatter.ofPattern("yyyyMMdd")).concat("0000");

        LocalDate endDt = LocalDate.now().plusDays(1);
        String parseEndDt = endDt.format(DateTimeFormatter.ofPattern("yyyyMMdd")).concat("0000");

        List<SmsFindListResponseDto> smsList = smsServiceClient.getSmsList(parseStatDt, parseEndDt).block();

        model.addAttribute("sms", smsList);
        return "sms-sendlist";
    }
}
