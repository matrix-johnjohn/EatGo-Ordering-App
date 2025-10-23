import { user } from '../../api/entity/user/index'
@Builder
export function HeaderCard(options:user){
  Row(){
    Image(options.avatar)
      .width(100)
      .margin({top:10,left:10})
      .borderRadius(10)

    Flex({direction:FlexDirection.Column,justifyContent:FlexAlign.SpaceEvenly}){
      Text(options.username)
        .fontSize(20)
        .fontWeight(FontWeight.Bold)
        .width('100%')
      Text(`邮箱:${options.email}`)
        .fontColor(Color.Gray)
        .width('100%')
    }
    .width('60%')
    .height(100)
    .margin({top:10,bottom:10,left:10})


  }
  .width('100%')
  .height(120)
  .backgroundColor(Color.White)
};