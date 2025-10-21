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
    .shadow({color:'#ff7e7e7e',radius:10,offsetX:5,offsetY:5})
  }
}