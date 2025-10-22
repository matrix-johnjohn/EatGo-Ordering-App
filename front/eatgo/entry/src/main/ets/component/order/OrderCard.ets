@Builder
export function OrderCardComing(){
  ListItem(){
    Row(){
      Image('https://p3-flow-imagex-sign.byteimg.com/tos-cn-i-a9rns2rl98/rc_gen_image/9595dcc5012d4dadb3fc921ee6a1d8e6preview.jpeg~tplv-a9rns2rl98-downsize_watermark_1_5.png?rcl=20251021203529C7A8A68F514DB07D68C7&rk3s=8e244e95&rrcfp=ddbb2dc7&x-expires=2076410144&x-signature=BC9mZ3C282w3OX6f%2BoOoQ4hz%2Fgs%3D')
        .width(100)
        .borderRadius(10)

      Flex({direction:FlexDirection.Column,justifyContent:FlexAlign.SpaceBetween,alignItems:ItemAlign.Start}){
        Text('kimo·轻食减脂餐')
          .fontSize(18)
          .fontWeight(FontWeight.Bold)
        Text('简介: 味道很不错')
          .fontColor(Color.Gray)
        Text('进行中')
          .padding({left:10,right:10,top:5,bottom:5})
          .borderRadius(5)
          .fontColor(Color.White)
          .backgroundColor('#FFC300')
      }
      .width('40%')
      .margin({left:10})

      Column().layoutWeight(1)

      Flex({direction:FlexDirection.Column,justifyContent:FlexAlign.SpaceBetween,alignItems:ItemAlign.End}){
        Row(){
          Image($rawfile('home/icon/ic_rmb.svg'))
            .width(25)
            .margin({right:5})
          Text("30")
            .textAlign(TextAlign.End)
            .fontSize(20)
            .fontColor('#FFC300')
        }
        .alignItems(VerticalAlign.Center)

        Text('取消订单')
          .textAlign(TextAlign.End)
          .padding(10)
          .border({width:2,color:Color.Gray,radius:10})
      }
      .height(100)

    }.width('98%')
    .margin({left:'1%',top:10})
    .padding(5)
    .height(110)
    .backgroundColor(Color.White)
    .borderRadius(10)
  }
}

@Builder
export function OrderCardAlready(){
  ListItem(){
    Row(){
      Image('https://p3-flow-imagex-sign.byteimg.com/tos-cn-i-a9rns2rl98/rc_gen_image/9595dcc5012d4dadb3fc921ee6a1d8e6preview.jpeg~tplv-a9rns2rl98-downsize_watermark_1_5.png?rcl=20251021203529C7A8A68F514DB07D68C7&rk3s=8e244e95&rrcfp=ddbb2dc7&x-expires=2076410144&x-signature=BC9mZ3C282w3OX6f%2BoOoQ4hz%2Fgs%3D')
        .width(100)
        .borderRadius(10)

      Flex({direction:FlexDirection.Column,justifyContent:FlexAlign.SpaceBetween,alignItems:ItemAlign.Start}){
        Text('kimo·轻食减脂餐')
          .fontSize(18)
          .fontWeight(FontWeight.Bold)
        Text('简介: 味道很不错')
          .fontColor(Color.Gray)
        Text('已出餐')
          .padding({left:10,right:10,top:5,bottom:5})
          .borderRadius(5)
          .fontColor(Color.White)
          .backgroundColor('#FFC300')
      }
      .width('40%')
      .margin({left:10})

      Column().layoutWeight(1)

      Flex({direction:FlexDirection.Column,justifyContent:FlexAlign.SpaceBetween,alignItems:ItemAlign.End}){
        Row(){
          Image($rawfile('home/icon/ic_rmb.svg'))
            .width(25)
            .margin({right:5})
          Text("30")
            .textAlign(TextAlign.End)
            .fontSize(20)
            .fontColor('#FFC300')
        }
        .alignItems(VerticalAlign.Center)

        Button('开吃')
          .type(ButtonType.Normal)
          .backgroundColor('#FFC300')
          .borderRadius(10)
      }
      .height(100)

    }.width('98%')
    .margin({left:'1%',top:10})
    .padding(5)
    .height(110)
    .backgroundColor(Color.White)
    .borderRadius(10)
  }
}

@Builder
export function OrderCardFinish(){
  ListItem(){
    Row(){
      Image('https://p3-flow-imagex-sign.byteimg.com/tos-cn-i-a9rns2rl98/rc_gen_image/9595dcc5012d4dadb3fc921ee6a1d8e6preview.jpeg~tplv-a9rns2rl98-downsize_watermark_1_5.png?rcl=20251021203529C7A8A68F514DB07D68C7&rk3s=8e244e95&rrcfp=ddbb2dc7&x-expires=2076410144&x-signature=BC9mZ3C282w3OX6f%2BoOoQ4hz%2Fgs%3D')
        .width(100)
        .borderRadius(10)

      Flex({direction:FlexDirection.Column,justifyContent:FlexAlign.SpaceBetween,alignItems:ItemAlign.Start}){
        Text('kimo·轻食减脂餐')
          .fontSize(18)
          .fontWeight(FontWeight.Bold)
        Text('简介: 味道很不错')
          .fontColor(Color.Gray)
        Text('已完成')
          .padding({left:10,right:10,top:5,bottom:5})
          .borderRadius(5)
          .fontColor(Color.White)
          .backgroundColor('#FF8D1A')
      }
      .width('40%')
      .margin({left:10})

      Column().layoutWeight(1)

      Flex({direction:FlexDirection.Column,justifyContent:FlexAlign.SpaceBetween,alignItems:ItemAlign.End}){
        Row(){
          Image($rawfile('home/icon/ic_rmb.svg'))
            .width(25)
            .margin({right:5})
          Text("30")
            .textAlign(TextAlign.End)
            .fontSize(20)
            .fontColor('#FF8D1A')
        }
        .alignItems(VerticalAlign.Center)

        Button('已完成')
          .type(ButtonType.Normal)
          .backgroundColor('#FF8D1A')
          .borderRadius(10)
      }
      .height(100)

    }.width('98%')
    .margin({left:'1%',top:10})
    .padding(5)
    .height(110)
    .backgroundColor(Color.White)
    .borderRadius(10)
  }
}