import { AxiosResponse } from '@ohos/axios';
import { UserCollection,CollectionQuery } from '../../entity/collection';
import { Dish, DishCate, DishQuery,DishTag } from '../../entity/menu';
import { instance,Result } from '../base/index';

//用户收藏列表
export const collectionList=(userId:number):Promise<AxiosResponse<Result<UserCollection[]>>>=>{
  return instance.get<Result<UserCollection[]>>(`/collection/list/${userId}`)
}

//收藏接口
export const collect=(options:CollectionQuery):Promise<AxiosResponse<Result<null>>>=>{
  return instance.post<Result<null>>('/collection/collect',options);
}

//取消收藏接口
export const cancelCollect=(options:CollectionQuery):Promise<AxiosResponse<Result<null>>>=>{
  return instance.post<Result<null>>('/collection/cancel/collect',options);
}

//收藏菜品数据
export const collectionDishList=(userId:number):Promise<AxiosResponse<Result<Dish[]>>>=>{
  return instance.get(`/collection/dish/list/${userId}`)
}