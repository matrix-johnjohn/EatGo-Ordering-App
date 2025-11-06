@Component
export struct TextTitle{
  @Prop title:string='';
  build() {
    Row(){
      Text(this.title)
        .width('100%')
        .textAlign(TextAlign.Center)
    }
    .width('100%')
    .height(70)
    .backgroundColor(Color.White)
    .shadow({color:'#ffdddddd',radius:10,offsetX:10,offsetY:10})
  }
}

@Component
export struct TextTitleBack{

  @Prop title:string='';

  build() {
    Row(){
      Image($rawfile('home/icon/ic_return.svg'))
        .fillColor('#ff999999')
        .width(30)
        .onClick(()=>{
          this.getUIContext().getRouter().back();
        })

      Text(this.title)
        .fontSize(15)

      Column()
    }
    .width('100%')
    .height(70)
    .backgroundColor(Color.White)
    .justifyContent(FlexAlign.SpaceBetween)
    .shadow({color:'#ffcdcdcd',radius:10,offsetX:10,offsetY:10})
  }
}