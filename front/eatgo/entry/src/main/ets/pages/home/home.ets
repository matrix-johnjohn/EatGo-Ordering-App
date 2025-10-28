import { goods } from '../../api/entity/goods/index';
import { GoodsCardBuilder } from '../../component/home/GoodCard';


@Component
export struct Home{

  @Builder topItemBuilder(text:string,icon:Resource){//标签栏Item
    ListItem(){
      Column({space:10}){
        Image(icon).width(50)
        Text(text)
      }
      .width('25%')
      .padding(10)
    }
  }

  build() {
    Column(){
      //头部搜索栏
      Row({space:5}){
          Search({placeholder:'搜索你想要的菜品',})
            .layoutWeight(1)
            .backgroundColor(Color.White)
            .placeholderColor(Color.Gray)
            .width('80%')
        }
        .width('100%')
        .padding(10)
        .backgroundColor('#FF7302')

        //顶部tab
        Scroll() {
          Row() {
            this.topItemBuilder('美食',$rawfile('home/top_tab/d_food.svg'))
            this.topItemBuilder('饮品',$rawfile('home/top_tab/drink.svg'))
            this.topItemBuilder('超市便利',$rawfile('home/top_tab/shop.svg'))
            this.topItemBuilder('蔬菜水果',$rawfile('home/top_tab/tomato.svg'))
            this.topItemBuilder('小吃宵夜',$rawfile('home/top_tab/x_nig.svg'))
        }
      }
      .scrollable(ScrollDirection.Horizontal)
      .scrollBar(BarState.Off)
      .width('100%')
      .margin({top:10,bottom:10})

      //Content
      Text('热门菜品')
        .width('95%')
        .margin({bottom:10})
        .fontWeight(FontWeight.Bold)

      Column(){
        List(){
          //菜品卡片
          GoodsCardBuilder(
            {
              title:'虾仁配蔬菜',
              image:'https://p3-flow-imagex-sign.byteimg.com/tos-cn-i-a9rns2rl98/rc_gen_image/7724d435864d4b6dbbc8f0ac5cff4b01preview.jpeg~tplv-a9rns2rl98-downsize_watermark_1_5.png?rcl=20251021203529C7A8A68F514DB07D68C7&rk3s=8e244e95&rrcfp=ddbb2dc7&x-expires=2076410144&x-signature=Dds4DesMHTFPrPS48T9vFuhJ8Co%3D',
              description:'好吃',
              price:22.1,
              collection_count:10
            } as goods)
        }
        .onReachEnd(()=>{
          console.log('滑动到底部了')
        })
      }
      .width('95%')
    }
    .width('100%')
    .height('100%')
  }
}