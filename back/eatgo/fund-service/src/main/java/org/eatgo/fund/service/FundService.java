package org.eatgo.fund.service;

import org.eatgo.common.domain.dto.FundDto;

public interface FundService {

    public void recharge(FundDto balanceDto);//根据email进行充值
}
