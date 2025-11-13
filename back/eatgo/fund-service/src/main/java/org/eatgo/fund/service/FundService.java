package org.eatgo.fund.service;

import org.eatgo.common.domain.dto.FundDto;
import org.eatgo.common.domain.query.DeductionQuery;

public interface FundService {

    public void recharge(FundDto balanceDto);//根据email进行充值

    public void withdraw(FundDto balanceDto);// 取款

    public void Deduct(DeductionQuery deductionQuery);
}
