@Builder
export function OrderCardComing(title:string,desc:string,image:string,setStatus:()=>void){

  ListItem(){
    Row(){
      Image(image)
        .width(100)
        .borderRadius(10)

      Flex({direction:FlexDirection.Column,justifyContent:FlexAlign.SpaceBetween,alignItems:ItemAlign.Start}){
        Text(title)
          .fontSize(18)
          .fontWeight(FontWeight.Bold)
        Text(desc)
          .fontColor(Color.Gray)
        Text('进行中')
          .padding({left:10,right:10,top:5,bottom:5})
          .borderRadius(5)
          .fontColor(Color.White)
          .backgroundColor('#FFC300')
      }
      .width('40%')
      .margin({left:10})

      Column().layoutWeight(1);

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

        Button('取消订单')
          .padding(10)
          .border({width:2,color:Color.Gray,radius:10})
          .backgroundColor(Color.White)
          .fontColor(Color.Black)
          .onClick(()=>{
            setStatus();
          })
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
export function OrderCardAlready(title:string,desc:string,image:string,setStatus:()=>void){
  ListItem(){
    Row(){
      Image(image)
        .width(100)
        .borderRadius(10)

      Flex({direction:FlexDirection.Column,justifyContent:FlexAlign.SpaceBetween,alignItems:ItemAlign.Start}){
        Text(title)
          .fontSize(18)
          .fontWeight(FontWeight.Bold)
        Text(desc)
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
          .onClick(()=>{
            setStatus();
          })
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
export function OrderCardFinish(title:string,desc:string,image:string,setStatus:()=>void){
  ListItem(){
    Row(){
      Image(image)
        .width(100)
        .borderRadius(10)

      Flex({direction:FlexDirection.Column,justifyContent:FlexAlign.SpaceBetween,alignItems:ItemAlign.Start}){
        Text(title)
          .fontSize(18)
          .fontWeight(FontWeight.Bold)
        Text(desc)
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

        Button('发表评论')
          .type(ButtonType.Normal)
          .backgroundColor('#FF8D1A')
          .borderRadius(10)
          .onClick(()=>{
            setStatus()
          })
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