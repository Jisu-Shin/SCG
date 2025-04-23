package com.example.SCG.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustInfo {
    Long custId;
    String phoneNumber;
    String custSmsConsentType;

    public CustInfo(CustListResponseDto cust){
        this.custId = cust.getId();
        this.phoneNumber = cust.getPhoneNumber();
        this.custSmsConsentType = cust.getConsentType();
    }
}
