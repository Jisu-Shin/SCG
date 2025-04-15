package com.example.SCG.web.controller;


import com.example.SCG.client.SmsServiceClient;
import com.example.SCG.web.dto.TemplateVariableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("/templateVariable")
public class TemplateVariableController {

    private final SmsServiceClient smsServiceClient;

    @PostMapping("/new")
    public Mono<String> create(TemplateVariableDto requestDto) {
        return smsServiceClient.createTemplateVariable(requestDto)
                .map(id -> "redirect:/smsTemplates/new")
                .onErrorReturn("template-create");
    }

}
