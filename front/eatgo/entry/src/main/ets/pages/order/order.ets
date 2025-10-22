import { TextTitle } from '../../component/common/CommonTitle';
import { OrderCardComing,OrderCardAlready,OrderCardFinish } from '../../component/order/OrderCard'
@Component
export struct Order{

  build() {
    Column(){
      //头部标签
      TextTitle({title:'我的订单'})

      List(){
        OrderCardComing();
        OrderCardAlready();
        OrderCardFinish()
      }.margin({top:10})

    }
    .width('100%')
    .height('100%')
  }
}