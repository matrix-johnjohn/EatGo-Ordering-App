import type { Result } from '../../entity/base';
import type { OrderTable } from '../../entity/order';
import { instance } from '../base/index';

// 订单表格列表
export const orderTableList=async():Promise<Result<OrderTable[]>>=>{
    const req=await instance.get('/order/order/table/list');

    return req.data;
}

// 搜索列表
export const searchOrderList=async(status:number):Promise<Result<OrderTable[]>>=>{
    const req=await instance.get('/order/order/search/list',{params:{status}})

    return req.data;
}

// 出餐接口
export const orderReady=async(orderId:number):Promise<Result<string>>=>{
    const req=await instance.put(`/order/order/update/${orderId}`)

    return req.data;
}