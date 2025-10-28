import { instance,Result } from '../base/index';
import { LoginForm }from '../../entity/user/index';

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