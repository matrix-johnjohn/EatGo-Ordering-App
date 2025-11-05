package org.eatgo.fund.controller;

import jakarta.annotation.Resource;
import org.eatgo.common.domain.dto.FundDto;
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
}
