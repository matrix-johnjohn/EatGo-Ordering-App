import type { Result } from '../../entity/base';
import type { DishCategorize } from '../../entity/menu';
import { instance } from '../base/index';

// 分类列表接口
export const CateList = async (): Promise<Result<DishCategorize[]>> => {
    const req = await instance.get('/menu/cate/list');

    return req.data;
}