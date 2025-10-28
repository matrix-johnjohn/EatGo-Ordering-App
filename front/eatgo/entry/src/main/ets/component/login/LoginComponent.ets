import { sendEmailValid } from "../../api/request/user";
import { LoginForm } from "../../api/entity/user/index";
import { LoadingDialog } from "@kit.ArkUI";

@Component
export struct LoginBackGround{
  build() {
    Image($r('app.media.login_bg'))
      .width('100%')
      .height('100%')
      .expandSafeArea([SafeAreaType.SYSTEM], [SafeAreaEdge.TOP, SafeAreaEdge.BOTTOM])
  }
}

@Component
export struct LoginInput{
  @Prop icon:Resource=$r('app.media.email');
  @Prop placeHolder:string='';
  @Prop type:InputType=InputType.Normal;

  handlerChange:(e:string)=>void=()=>{}

  build() {
    Row(){
      Image(this.icon)
        .width(20)
      TextInput({placeholder:this.placeHolder})
        .fontColor(Color.White)
        .backgroundColor(Color.Transparent)
        .placeholderColor(Color.White)
        .type(this.type)
        .onChange(this.handlerChange)
    }
    .width('90%')
    .border({width:{bottom:2},color:Color.White})
    .margin({bottom:10})
  }
}

@Component
export struct LoginButton{
  @Prop label:string='';
  @Prop color:string='';
  handlerAction: ()=>void=()=>{};

  build() {
    Button(this.label)
      .borderRadius(10)
      .width('90%')
      .height(50)
      .backgroundColor(this.color)
      .margin({bottom:15})
      .onClick(this.handlerAction)
  }
}



@Component
export struct LoginValidCode{

  dialogFunction(message:string){
    const dialog=new CustomDialogController({
      builder: LoadingDialog({
        content: message,
        themeColorMode: ThemeColorMode.DARK,
      })
    });

    return dialog;
  }

  dialogController: CustomDialogController = new CustomDialogController({
    builder: LoadingDialog({
      content: '邮箱发送成功',
      themeColorMode: ThemeColorMode.LIGHT,
    })
  });

  @Prop email:string='';

  handlerChange:(v:string)=>void=()=>{};


  build() {
    Row(){
      LoginInput({
        icon:$r('app.media.valid_code'),
        placeHolder:'邮箱验证码',
        handlerChange:(this.handlerChange)
      }).width('70%')
        .margin({bottom:30,right:0})
      Button('获取验证码')
        .width('30%')
        .borderRadius(15)
        .backgroundColor('#FFC300')
        .onClick(()=>{
          sendEmailValid({"email":this.email} as LoginForm).then((res)=>{
            if (res.data.code===200){
              console.log('邮箱合法');
              let dialog=this.dialogFunction('消息发送中...');

              dialog.open();

              setTimeout(()=>{
                let done=this.dialogFunction('消息发送完毕');

                dialog.close();

                done.open();
                done.close();
              },3000)



            }else if (res.data.code===10001){
              console.log('邮箱非法')
              //邮箱非法
              this.getUIContext().showAlertDialog(
                {
                  title: '邮箱非法警告',
                  message: res.data.message,
                  autoCancel: true,
                  alignment: DialogAlignment.Top,
                  offset: { dx: 0, dy: -20 },
                  gridCount: 3,
                  transition: TransitionEffect.asymmetric(TransitionEffect.OPACITY
                    .animation({ duration: 1000, curve: Curve.Sharp })
                    .combine(TransitionEffect.scale({ x: 1.5, y: 1.5 })
                      .animation({ duration:1000, curve: Curve.Sharp })),
                    TransitionEffect.OPACITY.animation({ duration: 100, curve: Curve.Smooth })
                      .combine(TransitionEffect.scale({ x: 0.5, y: 0.5 })
                        .animation({ duration: 100, curve: Curve.Smooth }))),
                  confirm: {
                    value: '确定',
                    action: () => {
                      console.info('Button-clicking callback');
                    }
                  },
                  cancel: () => {
                    console.info('Closed callbacks');
                  }
                }
              )
            }
          })
        })
    }.width('90%')
    .alignItems(VerticalAlign.Top)
    .justifyContent(FlexAlign.Start)
  }
}

@Component
export struct LoginLink{

  @Prop text:string='';

  updatePasswd=():void=>{}

  build() {
    Text(this.text)
      .fontColor(Color.White)
      .margin({bottom:50})
      .onClick(this.updatePasswd)
  }
}