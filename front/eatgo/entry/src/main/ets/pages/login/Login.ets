import { LoginBackGround,LoginInput,LoginButton,LoginValidCode,LoginLink } from '../../component/login/LoginComponent';
import { LoginForm } from '../../api/entity/user';
import { LoginTextStatus } from '../../utils/login/index';
import { login, register, resetPassword }from '../../api/request/user/index';
import { LoadingDialog, promptAction } from '@kit.ArkUI';

@Builder
export function LoginBuilder() {
  Login();
}

@Entry
@Component
struct Login {

  @State loginState:number=1;//1.登录,2.注册,3.修改密码

  @State loginData:LoginForm={username:'',email:'',password:'',validCode:''};//登录表单数据

  pathStack: NavPathStack = new NavPathStack();//控制跳转的对象

  dialogFunction(message:string){
    const dialog=new CustomDialogController({
      builder: LoadingDialog({
        content: message,
        themeColorMode: ThemeColorMode.DARK,
      })
    });

    return dialog;
  }
  build() {
    NavDestination(){
      Column(){
        Stack({alignContent:Alignment.Bottom}){
          LoginBackGround();//背景图片
          Flex({justifyContent:FlexAlign.Center,alignItems:ItemAlign.Center}){
            Column(){
              Image($r('app.media.logo')).width(100).margin({bottom:50})
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
                  PersistentStorage.persistProp<string>('token','123456');
                  login({
                    username:this.loginData.username,
                    email:this.loginData.email,
                    password:this.loginData.password,
                    validCode:this.loginData.validCode,
                  }).then((res)=>{
                    console.log(JSON.stringify(res))
                    if(res.data.code===200){//200
                      PersistentStorage.persistProp<string>('token',res.data.data);
                      console.log(JSON.stringify(res.data.data));
                      promptAction.openToast({
                        message:res.data.message,
                        duration:2000,
                        bottom:100
                      })
                      this.pathStack.pushPathByName('Layout',null,false);
                    }

                    if (res.data.code===10002){
                      console.log('报错处理','')
                      console.log(JSON.stringify(res.data.data));
                      promptAction.openToast({
                        message:res.data.message,
                        duration:2000,
                        bottom:100
                      })
                    }

                    if(res.data.code===10003){
                      this.dialogFunction('用户名或密码错误')
                      promptAction.openToast({
                        message:res.data.message,
                        duration:2000,
                        bottom:100
                      })
                    }
                  })
                }});
              }
              //注册
              LoginButton({label:LoginTextStatus(this.loginState),color:'#FF7302',handlerAction:()=>{
                if(this.loginState===1){//跳转注册
                  this.loginState=2;
                }else if(this.loginState===2){//注册接口

                  register(this.loginData).then((res)=>{
                    console.log(JSON.stringify(res))
                    if (res.data.code===200) {
                      this.loginState=1;//跳转页面
                      promptAction.openToast({
                        message:res.data.message,
                        duration:2000,
                        bottom:100
                      })
                    }else{
                      promptAction.openToast({
                        message:res.data.message,
                        duration:2000,
                        bottom:100
                      })
                    }
                  }).catch(()=>{
                    console.log('error')
                  })
                }
                else if(this.loginState===3){//忘记密码接口
                  resetPassword(this.loginData).then((result)=>{
                    console.log(JSON.stringify(result))
                    if (result.code===200) {
                      promptAction.openToast({
                        message:result.data.message,
                        duration:2000,
                        bottom:100
                      }).catch((error:object) => {
                        console.log(JSON.stringify(error));
                      })
                    }else{
                      promptAction.openToast({
                        message:result.data.message,
                        duration:4500,
                        bottom:100
                      }).catch((error:object) => {
                        console.log(JSON.stringify(error));
                      })
                    }
                  })
                  this.loginState=1;
                }
                console.log(JSON.stringify(this.loginData));
              }});

              //忘记密码
              if(this.loginState===1){
                LoginLink({text:'忘记密码?前去修改!',updatePasswd:()=>{
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
    .hideTitleBar(true)
    .onReady((context: NavDestinationContext) => {
      this.pathStack = context.pathStack;
    })
  }
}