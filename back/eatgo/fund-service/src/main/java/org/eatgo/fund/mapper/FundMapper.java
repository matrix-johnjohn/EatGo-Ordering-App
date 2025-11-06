package org.eatgo.fund.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.eatgo.common.domain.dto.FundDto;
import org.eatgo.common.domain.po.RechargeRecord;
import org.eatgo.common.domain.po.User;

@Mapper
public interface FundMapper {

    @Update("update user set balance=balance+#{amount} where email=#{email}")
    public void recharge(FundDto dto); // 充值

    @Update("update user set balance=balance-#{amount} where email=#{email}")
    public void withdraw(FundDto dto); // 取款

    @Insert("insert into user_recharge_record (user_id,method,amount) values (#{userId},#{method},#{amount});")
    public void insertRechargeRecord(RechargeRecord record); // 添加充值记录
}
