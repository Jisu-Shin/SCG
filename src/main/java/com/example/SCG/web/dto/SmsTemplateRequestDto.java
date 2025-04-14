package com.example.SCG.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SmsTemplateRequestDto {

    private String templateContent;
    private String smsType;

    @Override
    public String toString() {
        return "SmsTemplateRequestDto{" +
                "templateContent='" + templateContent + '\'' +
                ", smsType='" + smsType + '\'' +
                '}';
    }
}
