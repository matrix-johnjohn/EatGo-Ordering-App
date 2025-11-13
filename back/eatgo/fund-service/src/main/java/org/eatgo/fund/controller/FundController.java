package org.eatgo.fund.controller;

import jakarta.annotation.Resource;
import org.eatgo.common.domain.dto.FundDto;
import org.eatgo.common.domain.query.DeductionQuery;
import org.eatgo.common.domain.vo.ResultVo;
import org.eatgo.fund.service.FundService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fund")
public class FundController {

    @Resource(name = "wechat")
    private FundService fundServiceWechat;

    @Resource(name = "alipay")
    private FundService fundServiceAlipay;

    @Resource(name="common")
    private FundService fundServiceCommon;

    @PutMapping("/wx/recharge")// 微信充值接口
    public ResultVo<String> wxRecharge(@RequestBody FundDto fundDto) {
        fundServiceWechat.recharge(fundDto);
        return ResultVo.success("微信充值","充值成功");
    }

    @PutMapping("/ali/recharge")// 支付宝充值接口
    public ResultVo<String> aliRecharge(@RequestBody FundDto fundDto) {
        fundServiceAlipay.recharge(fundDto);
        return ResultVo.success("支付宝充值","充值成功");
    }

    @PutMapping("/wx/withdraw")// 微信提现接口
    public ResultVo<String> wxWithdraw(@RequestBody FundDto fundDto) {
        fundServiceWechat.withdraw(fundDto);
        return ResultVo.success("微信取款","取款成功");
    }

    @PutMapping("/ali/withdraw")// 支付宝提现接口
    public ResultVo<String> aliWithdraw(@RequestBody FundDto fundDto) {
        fundServiceAlipay.withdraw(fundDto);
        return ResultVo.success("支付宝取款","取款成功");
    }

    @PutMapping("/common/deduct")
    public ResultVo<String> deduct(@RequestBody DeductionQuery deductionQuery) {
        fundServiceCommon.Deduct(deductionQuery);

        return ResultVo.success("购买成功",null);
    }
}
