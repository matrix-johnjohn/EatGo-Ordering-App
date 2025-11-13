export interface DishCate{
  "id": number,
  "name": string,
  "icon": string,
  "banner": string
}

export interface DishTag{
  "id"?:number,
  "name"?:string,
  "categorizeId"?: number,
  "banner"?:string[]
}

export interface Dish{
  id?:number;
  title?:string;
  description?: string;
  image?: string;
  price?: number;
  categorizeId?:number;
  tagId?:number;
  collectionCount?:number;
}

export interface DishQuery{
  categorizeId?: number;
  tagId?: number;
}

export interface DeductQuery{
  "userId":number,
  "price":number,
  "email":string
}