
@Builder
export function EmptyStatusForDish(text:string){
  Column(){
    Image($rawfile('component/empty_status.svg'))
      .width('30%')
    Text(text)
      .fontColor('#ff7e7e7e')
  }
  .width('100%')
  .margin({top:40})
}