import { Dish, DishQuery, DishTag } from '../../../api/entity/menu/index';
import { Router } from '@ohos.arkui.UIContext';
import { dishList, tagList } from '../../../api/request/menu';
import { AxiosResponse } from '@ohos/axios';
import { Result } from '../../../api/request/base';
import { EmptyStatusForDish } from '../../../component/common/EmptyStatus';
import { UserInfo } from '../../../api/entity/user';
import { cancelCollect, collect, collectionList } from '../../../api/request/collection';
import { isCollection, UserCollection } from '../../../api/entity/collection';
import { preferences } from '@kit.ArkData';
import { getUser } from '../../../api/request/user';

@CustomDialog
@Component
struct CustomDialogSelectSpecs{
  @Link dish:Dish;
  controller?: CustomDialogController;

  aboutToAppear(): void {
    console.log('当前选择菜品',JSON.stringify(this.dish));
  }
  build() {
    Column(){

    }
    .width('100%')
    .height(300)
    .backgroundColor(Color.White)
  }
}

@Entry
@Component
export struct CateDetail {
  // 当前菜品
  @State currentDish:Dish={};
  dialogSelectSpecsController: CustomDialogController | null = new CustomDialogController({
    builder: CustomDialogSelectSpecs({
      dish:this.currentDish
    })
  });
  // 用户身份信息
  @State info:UserInfo= {
    id: 0,
    username: '',
    password: '',
    email: '',
    balance: 0,
    isEffective: 0,
    auth: 0,
    avatar: ''
  };

  @State currHighLightIndex:number=0;//当前标签,0:全部,other:当前标签id

  @State pageData:DishTag={};//轮播图和分类id(分类数据)

  @State dishTagList:DishTag[]=[];//标签列表数据(标签数据)

  @State dishList:Dish[]=[];//菜品列表数据(列表数据)

  @State collectionList:UserCollection[]=[];//用户收藏数据

  @State queryData:DishQuery={};//查询菜品列表封装数据(菜品请求分类数据)
  //设置轮播图数据和分类id
  setBanners():void{//设置当前页面的轮播海报和分类id数据
    let options=this.getUIContext().getRouter().getParams();
    let result=options as DishTag;
    this.pageData.categorizeId=result.categorizeId;
    this.pageData.banner=result.banner;
    this.queryData.categorizeId=result.categorizeId;
  }
  //设置菜品标签列表数据
  setDishTagList():void{
    tagList(this.pageData.categorizeId as number)
      .then((res:AxiosResponse<Result<DishTag[]>>)=>{
        this.dishTagList=res.data.data;
      })
  }
  //设置菜品列表数据
  setDishList(query:DishQuery):void{
    dishList(query).then((res:AxiosResponse<Result<DishTag[]>>)=>{
      // console.log('appear:dishList',JSON.stringify(res.data.data));

      this.dishList=res.data.data;
    })
  }
  //设置用户收藏数据
  setCollectionList(userId:number):void{
    collectionList(userId)
      .then((res:AxiosResponse<Result<UserCollection[]>>)=>{
        // console.log('success');
        console.log('收藏数据',JSON.stringify(res.data.data));

        this.collectionList=res.data.data;
      }).catch(()=>{
      console.log('error');
    })
  }

  @Builder//返回上一级和搜索框的渲染函数
  BackAndSearchers(){
    Row(){
      Image($rawfile('home/icon/ic_return.svg'))
        .fillColor(Color.White)
        .margin({left:10,top:5})
        .onClick(() => {
          (this
            .getUIContext()
            .getRouter() as Router)
            .back();
        })
        .width(25)
        .height(25)

      Search({placeholder:'搜索菜品'})
        .margin({right:10,top:5})
        .backgroundColor(Color.White)
        .width('80%')
    }
    .width('100%')
    .justifyContent(FlexAlign.SpaceBetween)
  }

  @Builder//轮播图渲染
  BannerRender(){
    Swiper(){
      ForEach(this.pageData.banner,(item:string)=>{
        Image(item)
          .width('100%')
      })
    }
    .width('100%')
    .autoPlay(true)
    .interval(3000)
    .displayArrow({
      showBackground: true,
      isSidebarMiddle: true,
      backgroundSize: 24,
      arrowSize: 18
    }, false)
    .loop(true)
  }

  @Builder//标签列表渲染渲染
  TagListRender(){
    Scroll(){
      Row(){
        Column(){
          Text('全部菜品')
            .margin({left:20,right:20,bottom:15})
        }
        .border({width:{bottom:this.currHighLightIndex===0?5:0},color:Color.Orange})
        .onClick(()=>{
          this.currHighLightIndex=0;//调整当前高亮块的id索引

          this.queryData.tagId=0;//设置封装查询数据

          console.log('test1',JSON.stringify(this.queryData));

          this.setDishList(this.queryData);
          // 封装前菜品数据
          // dishList(this.queryData).then((res:AxiosResponse<Result<DishTag[]>>)=>{
          //   console.log(JSON.stringify(res.data.data));
          //
          //   this.dishList=res.data.data;
          // })
        })
        ForEach(this.dishTagList,(item:DishTag)=>{
          Column(){
            Text(item.name)
              .margin({left:20,right:20,bottom:15})
          }
          .border({width:{bottom:this.currHighLightIndex===(item.id as number)?5:0},color:Color.Orange})
          .onClick(()=>{
            this.currHighLightIndex=(item.id as number);//调整当前高亮块的id索引

            this.queryData.tagId=item.id;//设置封装查询数据

            console.log('test1',JSON.stringify(this.queryData));
            this.setDishList(this.queryData);
            // dishList(this.queryData).then((res:AxiosResponse<Result<DishTag[]>>)=>{
            //   console.log(JSON.stringify(res.data.data));
            //
            //   this.dishList=res.data.data;
            // })
          })
        })
      }
    }
    .width('100%')
    .margin({top:20,bottom:20})
    .scrollBar(BarState.Off)
    .scrollable(ScrollDirection.Horizontal)
  }

  @Builder//菜品数据渲染
  DishListRender(){
    List(){
      ForEach(this.dishList,(item:Dish,index:number)=>{
        ListItem(){
          Row(){
            Image(item.image)
              .width(90)
              .margin({left:5})
            //texts
            Column(){
              Text(item.title)
                .textAlign(25)
                .fontWeight(FontWeight.Bold)
                .width('100%')
              Text(`${item.description?.toString().substring(0,5)}...`)
                .width('100%')
                .margin({top:5})
                .fontColor(Color.Gray)
                .fontSize(15)
              Row(){
                Image($rawfile('home/icon/ic_rmb.svg')).width(20)
                Text('20')
              }
              .width('100%')
              .margin({top:10})
            }
            .width('30%')
            .height('100%')
            .margin({left:5})
            .padding({top:10,bottom:10})
            //过渡
            Column(){}.layoutWeight(1)
            //buttons
            Row(){
              Button('选规格')
                .fontSize(12)
                .backgroundColor(Color.Orange)
                .margin({right:5})
                .onClick((event: ClickEvent) => {
                  this.currentDish=this.dishList[index];
                  // Todo:启动弹窗
                  this.dialogSelectSpecsController?.open();
                })

              //按钮状态审查
              if(isCollection(this.collectionList,item.id as number)){
                Button('收藏')
                  .fontSize(12)
                  .backgroundColor('#FFC300')
                  .onClick((event: ClickEvent) => {
                    console.log('id-test',item.id?.toString(),JSON.stringify(this.collectionList.length));

                    //数据库添加数据
                    collect({
                      userId:this.info.id
                    ,dishId:item.id as number
                    }).then(()=>{
                      console.log((AppStorage.get<UserInfo>('info')?.id as number).toString())
                    })
                    //页面添加数据
                    this.collectionList.push({
                      userId: this.info.id,
                      dishId: item.id as number,
                      id: 0,
                      createTime: '',
                      updateTime: ''
                    })
                  })
              }else{
                Button('取消收藏')
                  .fontSize(12)
                  .backgroundColor('#ff7e7e7e')
                  .onClick((event: ClickEvent) => {
                    //数据库删除数据
                    cancelCollect({
                      userId:this.info.id
                    ,dishId:item.id as number
                    }).then(()=>{
                      console.log('删除成功')
                    })
                    //页面删除数据
                    let index=0;
                    for (let i = 0; i < this.collectionList.length; i++) {
                      if (this.collectionList[i].dishId===item.id){
                        this.collectionList.splice(i,1);
                      }
                    }
                  })
              }
            }
            .margin({right:10})
          }
          .width('96%')
          .height(100)
          .margin({top:10})
        }
        .onClick(()=>{
          this.getUIContext().getRouter().pushUrl({url:"pages/home/DishDetail/index",params:item as Dish});
        })
      })
    }
    .scrollBar(BarState.Off)
    .width('100%')
    .height('60%')
  }
  aboutToAppear(): void {
    this.setBanners();//获取banner数据

    this.setDishTagList();//获取标签列表数据

    this.setDishList({categorizeId:this.pageData.categorizeId,tagId:0});//获取菜品数据



    let email=preferences.getPreferencesSync(this.getUIContext().getHostContext(),{name:'user'}).getSync("email","");
    getUser(email as string).then((res:AxiosResponse<Result<UserInfo>>)=>{
      console.log(JSON.stringify(res.data.data))

      this.info=res.data.data;

      this.setCollectionList(this.info.id);//获取用户收藏数据
    })
  }

  build() {
    //轮播图,返回上级,搜索栏
    Column() {
      Stack({alignContent:Alignment.Start}){
        this.BannerRender();
        this.BackAndSearchers();
      }
      .width('100%')
      .alignContent(Alignment.Top)

      Column(){
        //菜品标签列表
        this.TagListRender();

        Column(){
          Text('菜品列表')
            .width('100%')
            .margin({left:5})
            .textAlign(TextAlign.Start)
          //TODO:菜品列表
          if(this.dishList.length===0){
            EmptyStatusForDish('商家还没有推出菜品哦');
          }else{
            this.DishListRender();
          }
        }
      }
    }
    .width('100%')
    .height('100%')
    .expandSafeArea([SafeAreaType.SYSTEM], [SafeAreaEdge.TOP, SafeAreaEdge.BOTTOM])
  }
}