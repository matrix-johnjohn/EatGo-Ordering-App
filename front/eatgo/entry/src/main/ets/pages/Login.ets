@Component
struct LoginBackGround{
  build() {
    Image($r('app.media.login_bg'))
      .width('100%')
      .height('100%')
      .expandSafeArea([SafeAreaType.SYSTEM], [SafeAreaEdge.TOP, SafeAreaEdge.BOTTOM])
  }
}

@Component
struct LoginInput{
  @Prop icon:Resource=$r('app.media.email');
  @Prop placeHolder:string='';
  @Prop type:InputType=InputType.Normal;

  handlerChange:(e:string)=>void=()=>{}

  build() {
    Row(){
      Image(this.icon)
        .width(20)
      TextInput({placeholder:this.placeHolder})
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
struct LoginInputValid{
  getValidCode:()=>void=()=>{}
  build() {

  }
}

@Component
struct LoginButton{
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

@Entry
@Component
struct Login {
  build() {
    Column(){
      Stack({alignContent:Alignment.Bottom}){

        LoginBackGround();//背景图片

        Flex({justifyContent:FlexAlign.Center}){
          Column(){

            Image($r('app.media.logo')).width(200).margin({bottom:50})

            //邮箱地址
            LoginInput({
              icon:$r('app.media.email'),
              placeHolder:'邮箱地址',
              type:InputType.Normal,
              handlerChange:((e:string)=>{
              console.log('邮箱',e)
              })
            }).margin({bottom:5})

            //密码
            LoginInput({
              icon:$r('app.media.passwd'),
              placeHolder:'密码',
              type:InputType.Password,
              handlerChange:((e:string)=>{
                console.log('密码',e)
              })
            }).margin({bottom:5})

            //获取验证码
            Row(){
              LoginInput({
                icon:$r('app.media.valid_code'),
                placeHolder:'邮箱验证码',
                handlerChange:((e:string)=>{
                  console.log('验证码',e)
                })
              }).width('70%')
                .margin({bottom:30,right:0})
              Button('获取验证码')
                .width('30%')
                .borderRadius(15)
                .backgroundColor('#FFC300')
                .onClick(()=>{
                  console.log('获取验证码')
                })
            }.width('90%').alignItems(VerticalAlign.Top).justifyContent(FlexAlign.Start)

            //点击登录
            LoginButton({label:'点击登录',color:'#FFC300',handlerAction:()=>{
              console.log('点击登录');
            }});

            //跳转注册
            LoginButton({label:'点击注册',color:'#FF7302',handlerAction:()=>{
              console.log('点击注册');
            }});

            //忘记密码
            Text('忘记密码?前去找回!')
              .fontColor(Color.White)
              .margin({bottom:50})
          }.width('100%')
        }.width('100%')
      }
      .width('100%')
      .height('100%')
    }.width('100%')
    .height('100%')
    .backgroundColor(Color.Black)
  }
}

