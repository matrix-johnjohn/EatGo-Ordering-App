import { instance,Result } from '../base/index';
import { FundDto, LoginForm, UserInfo }from '../../entity/user/index';
import { AxiosResponse } from '@ohos/axios';
import { User } from '../../../pages/user/user';

// 发送邮箱验证码接口
export const sendEmailValid=(options:LoginForm):Promise<Result<Result<undefined>>>=>{
  return instance.post('/user/send', options,{headers:{'Authorization':'token1'}})
}

//登录接口
export const login=(options:LoginForm):Promise<Result<Result<string>>>=>{
  return instance.post('/user/login',options,{headers:{'Authorization':'token1'}})
}

//重设密码接口
export const resetPassword=(options:LoginForm):Promise<Result<Result<string>>>=>{
  return instance.post('/user/reset/passwd',options,{headers:{'Authorization':'token1'}})
}

//注册接口
export const register=(options:LoginForm):Promise<Result<Result<string>>>=>{
  return instance.put('/user/register',options,{headers:{'Authorization':'token1'}})
}

//验证token
export const verificationToken=(token:string,email:string):Promise<AxiosResponse<Result<boolean>>>=>{
  return instance.get(`/user/verification/token/${token}/${email}`);
}

//获取当前用户
export const getUser=(email:string):Promise<AxiosResponse<Result<UserInfo>>>=>{
  return instance.get(`/user/get/user/${email}`)
}

// 微信支付接口
export const wechatRecharge=(fundDto:FundDto):Promise<AxiosResponse<Result<string>>>=>{
  return instance.put('/fund/wx/recharge',fundDto)
}

// 支付宝支付接口
export const alipayRecharge=(fundDto:FundDto):Promise<AxiosResponse<Result<string>>>=>{
  return instance.put('/fund/ali/recharge',fundDto)
}

// 微信取款接口
export const wechatWithdraw=(fundDto:FundDto):Promise<AxiosResponse<Result<string>>>=>{
  return instance.put('/fund/ali/withdraw',fundDto);
}

// 支付宝取款接口
export const alipayWithdraw=(fundDto:FundDto):Promise<AxiosResponse<Result<string>>>=>{
  return instance.put('/fund/ali/withdraw',fundDto)
}

// 编辑用户
export const editUser=(user:UserInfo):Promise<AxiosResponse<Result<string>>>=>{
  return instance.put('/user/edit/user',user);
}