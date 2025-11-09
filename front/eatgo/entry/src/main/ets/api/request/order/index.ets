import { AxiosResponse } from '@ohos/axios';
import { OrderDto, OrderVo } from '../../entity/order';
import { instance,Result } from '../base/index';

//下单接口
export const insertOrder=(orderDto:OrderDto):Promise<AxiosResponse<Result<string>>>=>{
  return instance.put('/order/insert/order',orderDto);
}

//订单列表
export const orderList=(userId:number,status:number):Promise<AxiosResponse<Result<OrderVo[]>>>=>{
  return instance.get(`/order/list/${userId}/${status}`)
}

//更新订单
export const updateOrder=(orderId:number,status:number):Promise<AxiosResponse<Result<string>>>=>{
  return instance.put(`/order/update/order/${orderId}/${status}`)
}