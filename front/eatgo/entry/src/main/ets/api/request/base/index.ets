import axios from '@ohos/axios';

export const instance=axios.create({
  baseURL:'http://192.168.163.1:8080',
  headers:{'Authorization':'Bearer '},
  timeout:15000,
})

export interface Result<T>{
  code:number;
  message:string;
  data:T
}

