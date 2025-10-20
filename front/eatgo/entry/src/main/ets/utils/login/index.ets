//设置文字状态
export const LoginTextStatus=(status:number):string=>{
  if(status===1){
    return "跳转注册";
  }else if(status===2){
    return "立即注册";
  }else{
    return "修改密码";
  }

}