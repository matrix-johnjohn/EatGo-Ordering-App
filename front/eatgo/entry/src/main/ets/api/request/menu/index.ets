import { DishCate } from '../../entity/menu';
import { instance,Result } from '../base/index';

//分类列表接口
export const cateList=():Promise<Result<Result<DishCate[]>>>=>{
  return instance.get('/menu/cate/list',{headers:{'token':'admin111'}})
}