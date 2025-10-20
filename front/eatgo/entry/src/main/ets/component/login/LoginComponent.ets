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
          console.log('获取验证码',this.email)
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