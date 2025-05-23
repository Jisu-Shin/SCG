package com.example.SCG.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(title = "sms템플릿 전체 조회 응답 DTO")
public class SmsTemplateListResponseDto {
    private Long id;
    private String templateContent;
    private String smsType;
}
