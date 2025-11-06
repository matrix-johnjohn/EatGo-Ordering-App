import { UserInfo } from '../../api/entity/user';
import { HeaderCard } from '../../component/user/user';
import { preferences } from '@kit.ArkData';
import { getUser } from '../../api/request/user';
import { AxiosResponse } from '@ohos/axios';
import { Result } from '../../api/request/base/index'
import { collectionDishList } from '../../api/request/collection';
import { Dish } from '../../api/entity/menu';


@Component
export struct User {

  @State userInfo:UserInfo={//用户身份信息
    id: 0,
    username:'',
    password:'',
    email:'',
    balance:0,
    isEffective:0,
    auth: 0,
    avatar: ""
  };

  @State userCollectionDishList:Dish[]=[];

  aboutToAppear() {
    // 读取磁盘通道
    let p=preferences.getPreferencesSync(this.getUIContext().getHostContext(),{name:"user"});

    // 读取磁盘中的email
    let e=p.getSync("email",0) as string;

    //获取登录用户
    getUser(e).then((res:AxiosResponse<Result<UserInfo>>)=>{
      this.userInfo=res.data.data;
      //调用用户菜品数据
      collectionDishList(this.userInfo.id)
        .then((res:AxiosResponse<Result<Dish[]>>)=>{
        this.userCollectionDishList=res.data.data;
      })
      console.log(JSON.stringify(this.userInfo))
    })
  }

  build() {
    Column(){
      // 头部用户信息卡片
      HeaderCard({
        username:this.userInfo.username,
        avatar: this.userInfo.avatar as string,
        email:this.userInfo.email,
        onAction:()=>{
          this.getUIContext().getRouter().pushUrl({url:"pages/user/edit/edit"})}
      })
      // 收藏卡片
      Column(){
        Row(){
          Text('我的收藏')
            .margin({left:10,top:10})

          Image($rawfile('user/return.svg'))
            .width(25)
            .margin({left:10,top:10})
            .onClick(()=>{
              let router=this.getUIContext().getRouter();
              router.pushUrl({url:"pages/user/collection/UserCollection"})
            })
        }
        .width('100%')
        .justifyContent(FlexAlign.SpaceBetween)
        Row(){
          Scroll(){
            Row(){
              ForEach(this.userCollectionDishList.splice(0,5),(item:Dish,index)=>{
                Column(){
                  Image(item.image)
                    .width(80)
                    .margin({top:10,bottom:10})
                  Text(item.title)
                }
                .margin({top:10,bottom:10,left:10,right:this.userCollectionDishList.length-1!==index ? 10 : 0 })
              })
            }
          }
          .scrollable(ScrollDirection.Horizontal)
          .scrollBar(BarState.Off)
        }
        .width('100%')
        .borderRadius(10)

      }
      .width('94%')
      .margin({top :15})
      .borderRadius(10)
      .backgroundColor(Color.White)
      // 我的钱包
      Column(){
        Row(){
          Text('账户中心')
            .margin({left:10,top:10})
        }
        .width('100%')
        .justifyContent(FlexAlign.SpaceBetween)

        Row(){
          Image($rawfile('user/user_balance.svg'))
            .width(25)
          Text('我的余额')
            .margin({left:10})
          Column().layoutWeight(1)
          Text(`￥ ${this.userInfo.balance.toString()}`)
        }
        .width('90%')
        .margin({top:20,bottom:20})
        .padding(10)
        .border({width:{bottom:2},color:'#ff373737'})

        Row(){
          Image($rawfile('user/top_up.svg'))
            .width(25)
          Text('充值入口')
            .margin({left:10})
          Column().layoutWeight(1)
          Image($rawfile('user/return.svg'))
            .width(25)
            .onClick(()=>{
              this
                .getUIContext()
                .getRouter()
                .pushUrl({url:"pages/user/topup/TopUp",params:{email:this.userInfo.email}})
            })
        }
        .width('90%')
        .margin({bottom:20})
        .padding(10)
        .border({width:{bottom:2},color:'#ff373737'})
      }
      .width('94%')
      .margin({top :20})
      .borderRadius(10)
      .backgroundColor(Color.White)


    }
    .width('100%')
    .height('100%')
    .expandSafeArea([SafeAreaType.SYSTEM], [SafeAreaEdge.TOP, SafeAreaEdge.BOTTOM])
  }
}