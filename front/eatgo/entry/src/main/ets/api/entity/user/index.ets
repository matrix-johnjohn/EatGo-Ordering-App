export interface LoginForm{
  username:string;
  email:string;
  password:string;
  validCode:string;
};

export interface UserInfo{
  id:number;
  username:string;
  password:string;
  email:string;
  balance:number;
  isEffective:number;
  auth:number;
  avatar:string;
}

export interface user{
  username:string;
  email:string;
  avatar:string;
};