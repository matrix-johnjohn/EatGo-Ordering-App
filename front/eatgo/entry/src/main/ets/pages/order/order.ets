import { TextTitle } from '../../component/common/CommonTitle';

@Component
export struct Order{

  @Builder OrderCard(){

  }

  build() {
    Column(){
      //头部标签
      TextTitle({title:'我的订单'})


    }
    .width('100%')
    .height('100%')
  }
}