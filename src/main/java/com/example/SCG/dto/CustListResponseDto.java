package com.example.SCG.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustListResponseDto {

    private Long id;
    private String name;
    private String phoneNumber;
    private String consentType;
}
