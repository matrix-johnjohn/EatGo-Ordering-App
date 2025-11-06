import { goods } from '../../api/entity/goods/index';
import { Dish, DishCate, DishQuery,DishTag } from '../../api/entity/menu';
import { UserInfo } from '../../api/entity/user';
import { cateList, popularList } from '../../api/request/menu';
import { GoodsCardBuilder } from '../../component/home/GoodCard';
import { preferences } from '@kit.ArkData';
import { getUser } from '../../api/request/user';
import { AxiosResponse } from '@ohos/axios';
import { Result } from '../../api/request/base';
import { cancelCollect, collect, collectionList } from '../../api/request/collection';
import { isCollection, UserCollection } from '../../api/entity/collection';
import { promptAction } from '@kit.ArkUI';
@Component
export struct Home {

  //当前页数
  @State currentPage:number=1;
  //用户身份信息
  @State userInfo:UserInfo={
    id:15,
    username:'matrix',
    password:'123456',
    email:'1596903229@qq.com',
    balance:0,
    isEffective:1,
    auth: 0,
    avatar:""
  };
  //菜品分类列表
  @State Cates:DishCate[]=[];
  //用户收藏数据
  @State CollectionList:UserCollection[]=[];
  //热门菜品
  @State PopularDishList:Dish[]=[];


  aboutToAppear(): void {
    //磁盘通道
    let preference=preferences.getPreferencesSync(this.getUIContext().getHostContext(), {name:"user"});
    //获取用户邮箱
    let email=preference.getSync("email",'');
    //获取分类列表
    cateList().then((res)=>{
      this.Cates=res.data.data;
    })
    //获取用户身份信息
    getUser(email as string).then((res:AxiosResponse<Result<UserInfo>>)=>{
      //设置当前用户数据
      let u=res.data.data;
      this.userInfo=u;
      //获取当前用户收藏列表
      collectionList(u.id).then((res:AxiosResponse<Result<UserCollection[]>>)=>{
        this.CollectionList=res.data.data;
      })
      //热门菜品数据
      popularList({pageNum:this.currentPage,pageSize:10}).then((res:AxiosResponse<Result<Dish[]>>)=>{
        this.PopularDishList.push(...res.data.data);
      })
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
              ForEach(this.PopularDishList,(item:Dish,index:number)=>{
                GoodsCardBuilder(
                  {
                    title:item.title,
                    image:item.image,
                    description:item.description,
                    price:item.price,
                    collection_count:item.collectionCount,
                    isCollection:isCollection(this.CollectionList,item.id as number),
                    collect:()=>{collect({userId:this.userInfo.id,dishId:item.id as number}).then(()=>{
                      console.log('success');
                    })},
                    cancel:()=>{cancelCollect({userId:this.userInfo.id,dishId:item.id as number}).then(()=>{
                      this.CollectionList.splice(index,1);
                    })}
                  } as goods)
              })
            }
            .onReachEnd(()=>{
              this.currentPage++;

              popularList({pageNum:this.currentPage,pageSize:10}).then((res:AxiosResponse<Result<Dish[]>>)=>{
                console.log(JSON.stringify(res.data.data))
                console.log(this.currentPage.toString())
                this.PopularDishList.push(...res.data.data);
              })

              promptAction.openToast({message:"已经滑到底部了",duration:1000,bottom:100})

            })
          }
          .width('95%')
          .height('70%')
        }
        .width('100%')
        .height('100%')
  }
}