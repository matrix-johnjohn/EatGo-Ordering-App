import { AxiosResponse } from '@ohos/axios';
import { OrderDto } from '../../entity/order';
import { instance,Result } from '../base/index';

//下单接口
export const insertOrder=(orderDto:OrderDto):Promise<AxiosResponse<Result<string>>>=>{
  return instance.put('/order/insert/order',orderDto);
}