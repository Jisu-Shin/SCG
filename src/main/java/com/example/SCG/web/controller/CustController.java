package com.example.SCG.web.controller;

import com.example.SCG.client.CustServiceClient;
import com.example.SCG.web.dto.CustListResponseDto;
import com.example.SCG.web.dto.CustSaveErrorResponseDto;
import com.example.SCG.web.dto.CustSaveRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/custs")
public class CustController {

    private final CustServiceClient custServiceClient;

    @GetMapping("")
    public String getCustList(Model model) {
        List<CustListResponseDto> custList = custServiceClient.getCustAll().block();
        model.addAttribute("custs", custList);
        return "cust-findAll";
    }

    @GetMapping("/new")
    public String createCust() {
        return "cust-createForm";
    }

    @PostMapping("/new")
    public String save(@Valid CustSaveRequestDto requestDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            CustSaveErrorResponseDto.CustSaveErrorResponseDtoBuilder builder = CustSaveErrorResponseDto.builder();
            result.getFieldErrors().forEach(error -> {
                switch (error.getField()) {
                    case "name" -> {
                        builder.isErrorName(true);
                        builder.nameDefaultMsg(error.getDefaultMessage());
                    }

                    case "phoneNumber" -> {
                        builder.isErrorPhoneNumber(true);
                        builder.phoneNumberDefaultMsg(error.getDefaultMessage());
                    }

                    case "smsConsentType" -> {
                        builder.isErrorSmsConsentType(true);
                        builder.smsConsentTypeDefaultMsg(error.getDefaultMessage());
                    }
                }
            });

            model.addAttribute("errors", builder.build());
            model.addAttribute("requestDto", requestDto);
            return "cust-createForm";
        }

        Long custId = custServiceClient.createCust(requestDto).block();
        return "redirect:/custs";
    }
}
