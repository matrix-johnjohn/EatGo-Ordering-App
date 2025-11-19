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

export interface UpdateDishTagQuery{
    "id":number,
    "name":string,
    "cateId":number
}