import { DishTag } from '../../../api/entity/menu/index';
import { Router } from '@ohos.arkui.UIContext';
import { tagList } from '../../../api/request/menu';
import { AxiosResponse } from '@ohos/axios';
import { Result } from '../../../api/request/base';

@Entry
@Component
export struct CateDetail {

  @State currHighLightIndex:number=0;//当前标签,0:全部,other:当前标签

  @State pageData:DishTag={};//轮播图和分类id

  @State dishTagList:DishTag[]=[];//标签列表数据

  //设置轮播图数据和分类id
  setBanners():void{//设置当前页面的轮播海报和分类id数据
    let options=this.getUIContext().getRouter().getParams();
    let result=options as DishTag;
    this.pageData.categorizeId=result.categorizeId;
    this.pageData.banner=result.banner;
  }

  //设置标签列表数据
  setDishTagList():void{
    tagList(this.pageData.categorizeId as number).then((res:AxiosResponse<Result<DishTag[]>>)=>{
      this.dishTagList=res.data.data;
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

  @Builder
  TagListRender(){
    Scroll(){
      Row(){
        Column(){
          Text('全部菜品')
            .margin({left:20,right:20,bottom:15})
        }
        .border({width:{bottom:this.currHighLightIndex===0?5:0},color:Color.Orange})
        .onClick(()=>{
          this.currHighLightIndex=0;
        })
        ForEach(this.dishTagList,(item:DishTag)=>{
          Column(){
            Text(item.name)
              .margin({left:20,right:20,bottom:15})
          }
          .border({width:{bottom:this.currHighLightIndex===(item.id as number)?5:0},color:Color.Orange})
          .onClick(()=>{
            this.currHighLightIndex=(item.id as number);
          })
        })
      }
    }
    .width('100%')
    .margin({top:20,bottom:20})
    .scrollBar(BarState.Off)
    .scrollable(ScrollDirection.Horizontal)
  }





  aboutToAppear(): void {
    this.setBanners();//设置banner数据

    this.setDishTagList();//设置标签列表数据
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

        //菜品列表
        Column(){
          Text('菜品列表')
        }
      }


    }
    .width('100%')
    .height('100%')
    .expandSafeArea([SafeAreaType.SYSTEM], [SafeAreaEdge.TOP, SafeAreaEdge.BOTTOM])
  }
}