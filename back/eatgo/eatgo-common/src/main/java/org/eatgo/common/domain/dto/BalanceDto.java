package org.eatgo.common.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDto {
    private String email;
    private Integer money;
    private Integer method;//1.微信支付,2.支付宝支付
}
