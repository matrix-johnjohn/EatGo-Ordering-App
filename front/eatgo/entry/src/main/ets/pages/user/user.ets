import { UserInfo } from '../../api/entity/user';
import { HeaderCard } from '../../component/user/user';
import { preferences } from '@kit.ArkData';
import { getUser } from '../../api/request/user';
import { AxiosResponse } from '@ohos/axios';
import { Result } from '../../api/request/base/index'

@Component
export struct User {
  @State userInfo:UserInfo={//用户身份信息
    id:15,
    username:'matrix',
    password:'123456',
    email:'1596903229@qq.com',
    balance:0,
    isEffective:1,
    auth: 0,
    avatar: ""
  };

  aboutToAppear(): void {
    let p=preferences.getPreferencesSync(this.getUIContext().getHostContext(),{name:"user"});

    //读取磁盘中的email
    let e=p.getSync("email",0) as string

    //获取登录用户
    getUser(e).then((res:AxiosResponse<Result<UserInfo>>)=>{
      this.userInfo=res.data.data;
      console.log('登录用户数据',JSON.stringify(res));
    }).catch(()=>{
      console.log('error')
    })


  }

  build() {
    Column(){
      HeaderCard({
        username:this.userInfo.username,
        avatar:this.userInfo.avatar,
        email:this.userInfo.email
      })

      //收藏卡片
      Column(){
        Row(){
          Text('我的收藏')
            .margin({left:10,top:10})

          Image($rawfile('user/return.svg'))
            .width(25)
            .margin({left:10,top:10})
        }
        .width('100%')
        .justifyContent(FlexAlign.SpaceBetween)

        Row(){
          Scroll(){
            Row(){
              ForEach([1,1,1,1,1],(item:number,index)=>{
                Column(){
                  Image('http://192.168.174.130:9000/eatgo/dish/dumpling01.png')
                    .width(80)
                    .margin({top:10,bottom:10})
                  Text('招牌馄饨面')
                }
                .margin({top:10,bottom:10,left:10,right:[1,1,1,1,1].length-1===index?10:0})
              })
            }
          }
          .scrollable(ScrollDirection.Horizontal)
          .scrollBar(BarState.Off)
        }
        .width('100%')
        .borderRadius(10)

      }
      .width('96%')
      .margin({top :15})
      .backgroundColor(Color.White)


    }
    .width('100%')
    .height('100%')
  }
}