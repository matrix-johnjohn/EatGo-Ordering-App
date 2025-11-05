package org.eatgo.common.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundDto {
    private String email;
    private Integer amount;
    private Integer method;//0.微信支付,1.支付宝支付
}
