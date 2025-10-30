import { AxiosResponse } from '@ohos/axios';
import { DishCate, DishQuery,DishTag } from '../../entity/menu';
import { instance,Result } from '../base/index';

//分类列表接口
export const cateList=():Promise<Result<Result<DishCate[]>>>=>{
  return instance.get('/menu/cate/list',{headers:{'token':'admin111'}})
}

//标签列表接口
export const tagList=(id:number):Promise<AxiosResponse<Result<DishTag[]>>>=>{
  return instance.get<Result<DishTag[]>>(`/menu/tag/list/${id}`)
}

//菜品列表接口
export const dishList=(query:DishQuery):Promise<AxiosResponse<Result<DishTag[]>>>=>{
  return instance.get(`/menu/dish/list/${query.categorizeId}/${query.tagId}`);
}