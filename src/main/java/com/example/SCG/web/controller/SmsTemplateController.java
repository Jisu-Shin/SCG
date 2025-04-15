package com.example.SCG.web.controller;


import com.example.SCG.client.SmsServiceClient;
import com.example.SCG.web.dto.SmsTemplateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/smsTemplates")
public class SmsTemplateController {

    private final SmsServiceClient smsServiceClient;
//    private final TemplateVariableService templateVariableService;

    @GetMapping("/new")
    public String createTemplateForm(Model model) {
        model.addAttribute("templates", smsServiceClient.getSmsTemplates());
        model.addAttribute("placeholders", smsServiceClient.getTmpltVarList());
        return "template-create";
    }

    @PostMapping("/new")
    public Mono<String> createTemplate(SmsTemplateRequestDto requestDto) {
        return smsServiceClient.createSmsTemplate(requestDto)
                .map(id -> "redirect:/smsTemplates/new")
                .onErrorReturn("template-create");
    }

}
