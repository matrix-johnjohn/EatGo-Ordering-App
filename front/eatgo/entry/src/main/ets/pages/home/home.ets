import { goods } from '../../api/entity/goods/index';
import { DishCate, DishQuery,DishTag } from '../../api/entity/menu';
import { UserInfo } from '../../api/entity/user';
import { cateList } from '../../api/request/menu';
import { GoodsCardBuilder } from '../../component/home/GoodCard';
import { preferences } from '@kit.ArkData';
import { getUser } from '../../api/request/user';
import { AxiosResponse } from '@ohos/axios';
import { Result } from '../../api/request/base';

@Component
export struct Home {

  @State userInfo:UserInfo={//用户身份信息
    id:15,
    username:'matrix',
    password:'123456',
    email:'1596903229@qq.com',
    balance:0,
    isEffective:1,
    auth: 0,
    avatar:""
  };

  @State Cates:DishCate[]=[];//菜品分类列表

  aboutToAppear(): void {
    let preference=preferences.getPreferencesSync(
      this.getUIContext().getHostContext(),
      {name:"user"});
    //获取用户邮箱
    let email=preference.getSync("email",'');
    //获取分类列表
    cateList().then((res)=>{
      this.Cates=res.data.data;
    })
    //获取用户身份信息
    getUser(email as string).then((res:AxiosResponse<Result<UserInfo>>)=>{
      let u=res.data.data;

      this.userInfo=u;

      console.log('当前用户,id:',JSON.stringify(u));
    })
  }

  @Builder topItemBuilder(text:string,icon:Resource|string,id:number,banner:string){//标签栏Item
    ListItem(){
      Column({space:10}){
        Image(icon).width(50)
        Text(text)
      }
      .width('25%')
      .padding(10)
    }.onClick(()=>{

      let ban:string[]=JSON.parse(banner);

      PersistentStorage.persistProp<UserInfo>('info',this.userInfo);

      this.getUIContext()
        .getRouter()
        .pushUrl({url:'pages/home/CateDetail/index',params:{'categorizeId':id,'banner':ban,'userId':this.userInfo.id}})
        .catch(() => {
        // TODO: Implement error handling.
          console.log('wrong answer');
      })
    })
  }

  build() {
        Column(){
          //头部搜索栏
          Row({space:5}){
            Search({placeholder:'搜索你想要的菜品'})
              .layoutWeight(1)
              .backgroundColor(Color.White)
              .placeholderColor(Color.Gray)
              .width('80%')
          }
          .width('100%')
          .padding(10)
          .backgroundColor('#FF7302')

          //分类TagList
          Scroll() {
            Row() {
              ForEach(this.Cates,(e:DishCate)=>{
                this.topItemBuilder(e.name,e.icon,e.id,e.banner)
              })
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