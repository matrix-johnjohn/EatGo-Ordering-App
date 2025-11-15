import { instance } from '../base/index';
import type { UserQuery, User } from '../../entity/user';

// 发送验证码
export const sendValidCode = (option: UserQuery) => {
    return instance.post('/user/send', option);
}

// 用户列表
export const userList = () => {
    return instance.get('/user/user/list')
}

// 更改用户状态
export const updateUserEffective = (option: User) => {
    return instance.put('/user/update/effective', option)
}