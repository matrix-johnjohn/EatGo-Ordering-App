import { AxiosResponse } from '@ohos/axios';
import { ChatMessageQuery } from '../../entity/chat/index';
import { instance,Result } from '../base/index'

//历史记录
export const historyList=(query:ChatMessageQuery):Promise<AxiosResponse<Result<ChatMessageQuery[]>>>=>{
	return instance.post('/chat/history/list',query);
}

