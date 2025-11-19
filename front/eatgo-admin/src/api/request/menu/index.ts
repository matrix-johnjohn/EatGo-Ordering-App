import type { Result } from '../../entity/base';
import type { DishCategorize, DishTagVo, UpdateDishTagQuery } from '../../entity/menu';
import { instance } from '../base/index';

// 分类列表接口
export const CateList = async (): Promise<Result<DishCategorize[]>> => {
    const req = await instance.get('/menu/cate/list');

    return req.data;
}

// 删除分类接口
export const removeDishCate = async (item: DishCategorize): Promise<Result<string>> => {
    const req = await instance.delete(`/menu/delete/dish/cate/${item.id}`, { data: item });

    return req.data;
}

// 批量删除分类接口
export const removeDishCateList = async (item: number[]): Promise<Result<string>> => {
    const req = await instance.delete('/menu/delete/dish/cate/list', { params: { ids: item } });

    return req.data;
}

// 测试上传接口
export const upload = (file: FormData) => {
    return instance.put('/menu/upload', file);
}

// 删除轮播海报
export const removeBanner=async(options,index:number): Promise<Result<string>>=>{
    const req=await instance.put(`/menu/remove/banner/${index}`,options)

    return req.data;
}

// 更新分类
export const updateCate=async(file:FormData): Promise<Result<string>>=>{
    const req=await instance.put(`/menu/update/cate`,file)

    return req.data;
}

// 分类列表搜索
export const searchCateList=async(subString:string): Promise<Result<DishCategorize[]>>=>{
    const req=await instance.get(`/menu/search/cate/list/${subString}`)

    return req.data;
}

// 标签列表
export const tagAllList=async():Promise<Result<DishTagVo[]>>=>{
    const req=await instance.get('/menu/dish/tag/all/list');

    return req.data;
}

// 标签列表搜索
export const searchTagList=async(subName:string,cateId:number):Promise<Result<DishTagVo[]>>=>{
    const req=await instance.get('/menu/search/tag/list',{params:{subName,cateId}});

    return req.data;
}

// 新增标签
export const insertDishTag=async(name:string,cateId:number):Promise<Result<string>>=>{
    const req=await instance.put('/menu/insert/dish/tag',null,{params:{name,cateId}})

    return req.data;
}

// 删除标签
export const removeDishTag=async(tagId:number):Promise<Result<string>>=>{
    const req=await instance.delete(`/menu/delete/dish/tag/${tagId}`);

    return req.data;
}

// 批量删除标签
export const BatchRemoveDishTag=async(list:number):Promise<Result<string>>=>{
    const req=await instance.delete('/menu/batch/delete/dish/tag',{data:list});
    return req.data;
}

// 更新菜品标签
export const updateDishTag=async(dishTag:UpdateDishTagQuery):Promise<Result<string>>=>{
    const req=await instance.put('/menu/update/dish/tag/by',dishTag)
    return req.data;
}