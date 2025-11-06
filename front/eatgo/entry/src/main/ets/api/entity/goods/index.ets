export interface goods{
  title:string;//菜品标题
  image:string;//菜品图片url
  description:string;//菜品详情
  price:number;//价格
  collection_count:number;//菜品收藏数
  isCollection:boolean;//是否收藏
  collect:()=>void;//收藏
  cancel:()=>void;//取消收藏
}