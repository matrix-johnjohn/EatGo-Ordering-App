export interface DishCategorize {
    "key": number,
    "id": number,
    "name": string,
    "icon": string,
    "banner": string[],
    "createTime": string,
    "updateTime": string
}

export interface DishTagVo {
    "key":number;
    "id":number;
    "name":string;
    "cateName":string;
    "categorizeId":number;
    "createTime":string;
    "updateTime":string;
}

export interface DishVo{
    "key":number,
    "id": number,
    "title": string,
    "description": string,
    "image": string,
    "price": number,
    "categorizeId": number,
    "tagId": number,
    "cateName": string,
    "tagName": string,
    "collectionCount": number,
    "createTime": string,
    "updateTime": string
}

export interface DishDto{
    title:string,
    categorizeId:number,
    tagId:number,
    description:string,
    price:number
}

export interface UpdateDishTagQuery{
    "id":number,
    "name":string,
    "cateId":number
}

export interface DishSearchForm{
    "title":string,
    "dishCateId":number,
    "dishTagId":number
}