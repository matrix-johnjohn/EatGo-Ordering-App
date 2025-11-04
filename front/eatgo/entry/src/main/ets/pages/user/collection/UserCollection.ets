import { UserInfo } from '../../../api/entity/user';
import { getUser } from '../../../api/request/user';
import { TextTitleBack } from '../../../component/common/CommonTitle'
import { preferences } from '@kit.ArkData';
import { AxiosResponse } from '@ohos/axios';
import { Result } from '../../../api/request/base';
import { cancelCollect, collectionDishList } from '../../../api/request/collection';
import { Dish } from '../../../api/entity/menu';
import { promptAction } from '@kit.ArkUI';

@Entry
@Component
struct UserCollection{

  @State userInfo:UserInfo={
    id:0,
    username:'',
    password:'',
    email:'',
    balance:0,
    isEffective:0,
    auth:0,
    avatar:''
  };

  @State dishList:Dish[]=[];

  @Builder
  CollectionDishCardItem(image:string,title:string,desc:string,price:number,id:number,index:number){
    ListItem(){
      Scroll(){
        Row(){
          //卡片内容
          Row(){
            Image(image)
              .width(80)
              .margin({left:10})

            Column(){
              Column(){
                Text(title)
                  .textAlign(25)
                  .fontWeight(FontWeight.Bold)
                  .width('100%')
                Text(`${desc?.toString().substring(0,5)}...`)
                  .width('100%')
                  .margin({top:5})
                  .fontColor(Color.Gray)
                  .fontSize(15)
                Row(){
                  Image($rawfile('home/icon/ic_rmb.svg')).width(20)
                  Text(price.toString())
                }
                .width('100%')
                .margin({top:10})
              }
            }
            .margin({left:10})
          }
          .width('100%')
          .height('100%')
          .backgroundColor(Color.White)
          //滑动删除按钮
          Column(){
            Text('删除')
              .fontColor(Color.White)
              .fontSize(20)
              .width('100%')
              .height('100%')
              .textAlign(TextAlign.Center)
          }
          .width(150)
          .height('100%')
          .backgroundColor(Color.Red)
          .onClick(()=>{
            console.log(`${this.userInfo.id} ${id}`)

            this.dishList.splice(index,1);

            cancelCollect({dishId:id,userId:this.userInfo.id}).then(()=>{

              promptAction.openToast({
                message: '删除成功',
                duration: 3000,
              })

            })
          })
        }
      }
      .width('100%')
      .height(100)
      .scrollBar(BarState.Off)
      .scrollable(ScrollDirection.Horizontal)
      .borderRadius(5)
    }
    .width('100%')
    .margin({top:10})
  };

  aboutToAppear(): void {
    // 磁盘写入
    let p=preferences.getPreferencesSync(this.getUIContext().getHostContext(),{name:"user"});

    // 磁盘获取email
    let email=p.getSync("email",'');

    // 获取用户
    getUser(email as string).then((res:AxiosResponse<Result<UserInfo>>)=>{
      // 获取dishList
      collectionDishList(res.data.data.id).then((result:AxiosResponse<Result<Dish[]>>)=>{
        this.dishList=result.data.data;
      })
      this.userInfo=res.data.data;
    });


  }

  build() {
    Column(){
      TextTitleBack({
        title:`${this.userInfo.username} 的收藏`
      });

      List(){
        ForEach(this.dishList,(item:Dish,index)=>{
          this.CollectionDishCardItem(item.image as string,
            item.title as string,
            item.description as string,
            item.price as number,
            item.id as number,
            index
          )
        })
      }
      .width('96%')
      .height('80%')
      .margin({top:20})
    }
    .width('100%')
    .height('100%')
    .backgroundColor('#ffe9e9e9')
  }
}