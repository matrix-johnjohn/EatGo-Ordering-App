import { TextTitle } from '../../component/common/CommonTitle';
import { OrderCardComing,OrderCardAlready,OrderCardFinish } from '../../component/order/OrderCard'
import { preferences } from '@kit.ArkData';
import { getUser } from '../../api/request/user';
import { AxiosResponse } from '@ohos/axios';
import { Result } from '../../api/request/base';
import { OrderVo } from '../../api/entity/order';
import { UserInfo } from '../../api/entity/user';
import { orderList, updateOrder } from '../../api/request/order';
import { uploadComment } from '../../api/request/comment';
import { promptAction } from '@kit.ArkUI';

@CustomDialog
@Component
struct CustomDialogOrderComment {
  //评论内容
  @Link textValue: string;
  //菜品id
  @Link dishId:number;
  //用户id
  @State userId:number=0;
  //弹窗控制器
  controller?: CustomDialogController;

  aboutToAppear(): void {
    let email=preferences.getPreferencesSync(this.getUIContext().getHostContext(),{name:"user"}).getSync("email",'') as string;
    getUser(email).then((res:AxiosResponse<Result<UserInfo>>)=>{
      this.userId=res.data.data.id;
    })
  }

  build() {
    Column() {
      TextArea({
        text: this.textValue,
        placeholder: '请友善发言哦~',
      })
        .placeholderFont({ size: 16, weight: 400 })
        .width(336)
        .height(56)
        .margin(20)
        .fontSize(16)
        .fontColor('#182431')
        .backgroundColor('#FFFFFF')
        .maxLength(150)
        .showCounter(true, { thresholdPercentage: 50, highlightBorder: true })
        .onChange((value: string) => {
          this.textValue = value;
        })

      Button('提交评论')
        .onClick((event: ClickEvent) => {
          uploadComment({
            comment:this.textValue,
            userId:this.userId,
            dishId:this.dishId
          })
            .then((res:AxiosResponse<Result<string>>)=>{
              console.log(JSON.stringify(res.data.message));
              promptAction.openToast({
                message:"评论成功",
                duration:2000
              })
              this.textValue='';
              this.controller?.close();
            })
        })
    }
    .borderRadius(10)
  }
}

@Component
export struct Order{
  //评论信息
  @State textValue:string='';
  //菜品id
  @State dishId:number=0;
  //用户身份信息
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
  //用户订单列表
  @State orderList:OrderVo[]=[];
  //当前显示列表状态
  @State currentIndex:number=-1;
  //当前页面索引
  @Prop @Watch('change')num:number=1;

  //弹窗控制器
  dialogControllerComment: CustomDialogController=new CustomDialogController({
    builder:CustomDialogOrderComment({
      textValue: this.textValue,
      dishId:this.dishId
    })
  })
  change() {
    if (this.num===1){
      //磁盘读取email
      let email:string=preferences.getPreferencesSync(this.getUIContext().getHostContext(),{name:"user"}).getSync("email","") as string;
      //获取当前用户身份信息
      getUser(this.info.email).then((res:AxiosResponse<Result<UserInfo>>)=>{
        this.info=res.data.data;
      });
      //获取当前用户订单列表
      orderList(this.info.id,-1).then((res:AxiosResponse<Result<OrderVo[]>>)=>{
        this.orderList=res.data.data;
      });
    }
  }


  build() {
    Column(){
      //头部标签
      TextTitle({title:'我的订单'})

      //标签栏
      Row(){
        Text('全部')
          .padding({bottom:this.currentIndex===-1?10:0})
          .border({width:{bottom:this.currentIndex===-1?3:0},color:Color.Orange})
          .onClick(()=>{
            this.currentIndex=-1
            orderList(this.info.id,this.currentIndex).then((result:AxiosResponse<Result<OrderVo[]>>)=>{
              console.log(JSON.stringify(result.data.data));
              this.orderList=result.data.data;
            })
          })
        Text('制作中')
          .padding({bottom:this.currentIndex===0?10:0})
          .border({width:{bottom:this.currentIndex===0?3:0},color:Color.Orange})
          .onClick(()=>{
            this.currentIndex=0
            orderList(this.info.id,this.currentIndex).then((result:AxiosResponse<Result<OrderVo[]>>)=>{
              console.log(JSON.stringify(result.data.data));
              this.orderList=result.data.data;
            })
          })
        Text('已出餐')
          .padding({bottom:this.currentIndex===1?10:0})
          .border({width:{bottom:this.currentIndex===1?3:0},color:Color.Orange})
          .onClick(()=>{
            this.currentIndex=1
            orderList(this.info.id,this.currentIndex).then((result:AxiosResponse<Result<OrderVo[]>>)=>{
              console.log(JSON.stringify(result.data.data));
              this.orderList=result.data.data;
            })
          })
        Text('已完成')
          .padding({bottom:this.currentIndex===2?10:0})
          .border({width:{bottom:this.currentIndex===2?3:0},color:Color.Orange})
          .onClick(()=>{
            this.currentIndex=2
            orderList(this.info.id,this.currentIndex).then((result:AxiosResponse<Result<OrderVo[]>>)=>{
              console.log(JSON.stringify(result.data.data));
              this.orderList=result.data.data;
            })
          })
      }
      .margin({top:10})
      .width('100%')
      .justifyContent(FlexAlign.SpaceEvenly)

      List(){
        ForEach(this.orderList,(item:OrderVo,index:number)=>{
          if (item.status===0) {
            OrderCardComing(item.dishName,item.dishDesc,item.dishImg,()=>{
              console.log('取消订单',item.id,101);
              this.orderList.splice(index,1);
              updateOrder(item.id,101).then((res:AxiosResponse<Result<string>>)=>{
                console.log(JSON.stringify(res.data));
              })
            });
          }else if (item.status===1){
            OrderCardAlready(item.dishName,item.dishDesc,item.dishImg,()=>{
              console.log('开饭函数',item.id,1);
              this.orderList.splice(index,1);
              updateOrder(item.id,2).then((res:AxiosResponse<Result<string>>)=>{
                console.log(JSON.stringify(res.data));
              })
            });
          }else if(item.status===2){
            OrderCardFinish(item.dishName,item.dishDesc,item.dishImg,()=>{
              console.log('发表评论',item.id);
              this.dishId=item.dishId;
              this.dialogControllerComment.open();
            });
          }
        })
      }
      .width('100%')
      .height('80%')
      .margin({top:10})
      .scrollBar(BarState.Off)
    }
    .width('100%')
    .height('100%')
    .onAppear(()=>{
      let email=preferences.getPreferencesSync(this.getUIContext().getHostContext(),{name:"user"}).getSync("email","") as string;

      getUser(email).then((res:AxiosResponse<Result<UserInfo>>)=>{
        this.info=res.data.data;

        orderList(this.info.id,this.currentIndex).then((result:AxiosResponse<Result<OrderVo[]>>)=>{
          console.log(JSON.stringify(result.data.data));
        })
      })
    })
  }
}