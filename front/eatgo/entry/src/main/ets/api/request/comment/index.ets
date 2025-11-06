import { AxiosResponse } from '@ohos/axios';
import { comment } from '../../entity/comment';
import { instance,Result } from '../base/index';

//评论列表接口
export const commentList=(dishId:number):Promise<AxiosResponse<Result<comment[]>>>=>{
  return instance.get(`/comment/list/${dishId}`,{headers:{'token':'admin111'}})
}