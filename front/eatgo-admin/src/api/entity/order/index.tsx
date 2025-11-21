export interface OrderTable {
  key: number;
  id: number;
  userId: number;
  username: string;
  dishId: number;
  dishName: string;
  dishImg: string;
  dishDesc: string;
  price: number;
  count: number;
  totalPrice: number;
  status: number;
  createTime: string;
  updateTime: string;
}
