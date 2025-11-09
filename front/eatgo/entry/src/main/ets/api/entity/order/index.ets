export interface OrderDto{
  userId:number;
  dishId:number;
  price:number;
  count:number;
  totalPrice:number;
}

export interface OrderVo{
  "id": number,
  "userId": number,
  "dishId": number,
  "price": number,
  "count": number,
  "status": number,
  "totalPrice": number,
  "dishName": string,
  "dishImg": string,
  "dishDesc": string
}