import { LoginBackGround,LoginInput,LoginButton,LoginValidCode,LoginLink } from '../../component/login/LoginComponent';
import { LoginForm } from '../../api/entity/user';
import { LoginTextStatus } from '../../utils/login/index';
import router from '@ohos.router';


@Entry
@Component
struct Login {

  @State loginState:number=1;//1.登录,2.注册,3.修改密码

  @State loginData:LoginForm={username:'',email:'',password:'',validCode:''};//登录表单数据

  build() {
    Column(){
      Stack({alignContent:Alignment.Bottom}){
        LoginBackGround();//背景图片
        Flex({justifyContent:FlexAlign.Center,alignItems:ItemAlign.Center}){
          Column(){
            Image($r('app.media.logo')).width(200).margin({bottom:50})
            //用户名
            if(this.loginState===2){
              LoginInput({
                icon:$r('app.media.login_user_icon'),
                placeHolder:'用户名',
                type:InputType.Normal,
                handlerChange:((e:string)=>{
                  this.loginData.username=e;
                })
              }).margin({bottom:5})
            }
            //邮箱地址
            LoginInput({
              icon:$r('app.media.email'),
              placeHolder:'邮箱地址',
              type:InputType.Normal,
              handlerChange:((e:string)=>{
                this.loginData.email=e;
              })
            }).margin({bottom:5})
            //密码
            LoginInput({
              icon:$r('app.media.passwd'),
              placeHolder:'密码',
              type:InputType.Password,
              handlerChange:((e:string)=>{
                this.loginData.password=e;
              })
            }).margin({bottom:5})
            //获取验证码
            LoginValidCode({email:this.loginData.email,handlerChange:(v:string)=>{
              this.loginData.validCode=v;
            }})
            //点击登录
            if(this.loginState===1){
              LoginButton({label:'点击登录',color:'#FFC300',handlerAction:()=>{
                console.log('点击登录',JSON.stringify(this.loginData));
                PersistentStorage.persistProp<string>('token','123456');
                router.push({url:'pages/Index'})
              }});
            }
            //跳转注册
            LoginButton({label:LoginTextStatus(this.loginState),color:'#FF7302',handlerAction:()=>{
              console.log('跳转注册');
              if(this.loginState===1){//
                this.loginState=2;
              }else if(this.loginState===2){
                this.loginState=1;
              }
              else if(this.loginState===3){//跳转登录
                this.loginState=1;
              }
              console.log(JSON.stringify(this.loginData));
            }});

            //忘记密码
            if(this.loginState===1){
              LoginLink({text:'忘记密码?前去注册!',updatePasswd:()=>{
                this.loginState=3;
              }});
            }
          }.width('100%')
        }.width('100%')
        .height('100%')
      }
      .width('100%')
      .height('100%')
    }.width('100%')
    .height('100%')
    .backgroundColor(Color.Black)
  }
}