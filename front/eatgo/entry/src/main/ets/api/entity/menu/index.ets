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