package com.example.SCG.web.controller;


import com.example.SCG.client.SmsServiceClient;
import com.example.SCG.web.dto.SmsTemplateListResponseDto;
import com.example.SCG.web.dto.SmsTemplateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/smsTemplates")
public class SmsTemplateController {

    private final SmsServiceClient smsServiceClient;
//    private final TemplateVariableService templateVariableService;

    @GetMapping("/new")
    public String createTemplateForm(Model model) {
        List<SmsTemplateListResponseDto> tmpltList = smsServiceClient.getSmsTemplates().block();
        model.addAttribute("templates", tmpltList);
//        model.addAttribute("placeholders", templateVariableService.findAll());
        return "template-create";
    }

    @PostMapping("/new")
    public String createTemplate(SmsTemplateRequestDto requestDto) {
        log.info("템플릿생성");
        smsServiceClient.createSmsTemplate(requestDto).block();
        log.info("서버연결 완료");
        return "redirect:/smsTemplates/new";
    }

}
