import { User } from "../../../pages/user/user";

export interface UserCollection{
  id:0;
  userId:number;
  dishId:number;
  createTime:string;
  updateTime:string;
}


export interface CollectionQuery{
  userId:number;
  dishId:number
}
/*
 * true:收藏
 * false:取消收藏
 * */
export const isCollection=(collections:UserCollection[],dishId:number):boolean=>{
  let flag=true;

  collections.forEach((item:UserCollection):void=>{
    if(item.dishId===dishId){
      flag=false;
    }
  })

  return flag;
}