import type { ChatQuery } from '../../entity/chat';
import { instance } from '../base/index';

// 聊天记录
export const chatHistory=(options:ChatQuery)=>{
    return instance.post('/chat/history/list',options)
}