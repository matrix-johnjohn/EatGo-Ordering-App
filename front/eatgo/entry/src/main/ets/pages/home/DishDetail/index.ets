import { AxiosResponse } from "@ohos/axios";
import { comment } from "../../../api/entity/comment";
import { Dish } from "../../../api/entity/menu";
import { commentList } from "../../../api/request/comment";
import { TextTitleBack } from "../../../component/common/CommonTitle"
import { Result } from '../../../api/request/base';
import { EmptyStatusForDish } from "../../../component/common/EmptyStatus";

@Entry
@Component
export struct DishDetail{

  @State dish:Dish={};

  @State commentList:comment[]=[];

  @Builder CommentListRender(){
    List(){
      ForEach(this.commentList,(item:comment)=>{
        ListItem(){
          Row(){
            Image(item.avatar)
              .width(60)
              .margin({left:10,top:10})
              .borderRadius(50)

            Column(){
              Text(item.username)
                .fontSize(18)
                .fontWeight(FontWeight.Bold)
                .width('100%')

              Text(item.comment)
                .fontSize(14)
                .fontColor(Color.Gray)
                .width('100%')
                .margin({top:10})
            }
            .width('50%')
            .margin({left:10})
          }
          .width('100%')
          .height(80)
        }
      })
    }
    .width('100%')
  }

  aboutToAppear(): void {
    // 获取当前菜品数据
    let dish=this.getUIContext().getRouter().getParams();
    this.dish=dish as Dish;

    // 评论列表接口
    commentList(this.dish.id as number).then((res:AxiosResponse<Result<comment[]>>)=>{
      this.commentList=res.data.data;
    })
  }
  build() {
    Scroll(){
      Column(){
        TextTitleBack({title:this.dish.title})
        //菜品信息
        Image(this.dish.image).width('100%')
        Row(){
          Column(){
            Text(this.dish.title)
              .fontSize(18)
              .fontWeight(FontWeight.Bold)
              .width('100%')
              .textAlign(TextAlign.Start)
            Text(this.dish.description)
              .fontColor(Color.Gray)
              .fontSize(14)
              .margin({top:10})
              .width('100%')
              .textAlign(TextAlign.Start)
          }
          .width('50%')
          Text(`￥${this.dish.price}`)
            .fontSize(30)
            .fontWeight(FontWeight.Bold)
            .fontColor('#FF7302')
            .margin({right:10})
        }
        .width('96%')
        .justifyContent(FlexAlign.SpaceBetween)
        .margin(10)
        .padding(10)
        .backgroundColor(Color.White)
        .borderRadius(10)
        //用户评价信息
        Column(){
          Text('用户评价')
            .width('100%')
            .fontSize(15)
            .fontWeight(FontWeight.Bold)

          if(!this.commentList.length){
            EmptyStatusForDish('暂无评论');
          }else{
            this.CommentListRender()
          }
        }
        .margin({left:10,right:10,bottom:10})
        .padding(10)
        .backgroundColor(Color.White)
        .borderRadius(10)
      }
    }
    .width('100%')
    .height('100%')
    .scrollBar(BarState.Off)
    .backgroundColor('#ffe3e3e3')
  }
}