import { AxiosResponse } from '@ohos/axios';
import { UserInfo } from '../../../api/entity/user';
import { getUser } from '../../../api/request/user';
import { TextTitleBack } from '../../../component/common/CommonTitle';
import { Result } from '../../../api/request/base'
import { SelectItem,Param } from '../../../component/user/user';
import { preferences } from '@kit.ArkData';


@CustomDialog
struct CustomDialogTopUp {

  @State balance:number=0;

  @State currIndex: number | number[] = 0;

  @State paymentMethod: string[] = ['微信支付','支付宝支付'];//支付方式弹窗列表

  @State SelectItemList: SelectItem[]=[//充值方式列表
    { text:"微信支付",icon:$rawfile('user/weixin_icon.svg') },
    { text:'支付宝',icon:$rawfile('user/pay_icon.svg') }
  ];

  controller?: CustomDialogController;

  build() {
    Column() {
      Row(){
        Text('充值方式')
          .margin({left:20,right:20})
          .fontColor('#ff676767')
        Row(){
          Image(this.SelectItemList[this.currIndex as number].icon)
            .width(25)
            .margin({right:10})
          Text(this.SelectItemList[this.currIndex as number].text)
            .onClick(()=>{
              console.log(this.currIndex.toString())
            });
        }
        Column().layoutWeight(1);
        Image($rawfile('user/return.svg'))
          .width(25)
          .margin({right:10})
      }
      .width('100%')
      .height(100)
      .backgroundColor("#ffffffff")
      .onClick(()=>{
        this.getUIContext().showTextPickerDialog({
          range: this.paymentMethod,
          selected: this.currIndex,
          backgroundColor: undefined,
          backgroundBlurStyle: BlurStyle.Thin,
          alignment: DialogAlignment.Center,
          backgroundBlurStyleOptions: {
            colorMode: ThemeColorMode.LIGHT,
            adaptiveColor: AdaptiveColor.AVERAGE,
            scale: 1,
            blurOptions: { grayscale: [20, 20] },
          },
          selectedTextStyle:{color:'#ffff7302'},
          acceptButtonStyle:{fontColor:'#ff82ffff'},
          cancelButtonStyle:{fontColor:'#ffff7f7f'},
          onAccept: (value: TextPickerResult) => {
            this.currIndex = value.index;
            console.info(this.currIndex.toString());
          }
        });
      })
      Column(){
        Text('转入金额')
          .width('100%')
          .margin({left:30,top:15})

        Row(){
          Image($rawfile('user/rmb_icon.svg'))
            .width(40)

          TextInput()
            .type(InputType.Number)
            .backgroundColor(Color.Transparent)
            .onChange((e)=>{
              this.balance=parseInt(e);
            })
        }
        .width('90%')
        .margin({top:20,left:30,right:30})
        .border({width:{bottom:1},color:'#ffd4d4d4'})
        .padding({bottom:20})

        Button('转入')
          .width('80%')
          .backgroundColor("#FF7302")
          .margin({top:20})
          .onClick((event: ClickEvent) => {
            console.log(
              this.balance.toString(),
              preferences.getPreferencesSync(this.getUIContext().getHostContext(),
                {name:"user"}).getSync("email","").toString(),
                this.currIndex.toString())
            this.controller?.close();

            // Todo:充值接口嵌入
          })
      }
      .width('100%')
      .margin({bottom:30})

    }
  }
}

@Entry
@Component
struct TopUp{
  dialogControllerTopUp: CustomDialogController = new CustomDialogController({
    builder: CustomDialogTopUp({})
  })
  @State info:UserInfo={
    "id":15,
    "username":"matrix",
    "password":"123456",
    "email":"1596903229@qq.com",
    "balance":0,
    "isEffective":1,
    "auth":0,
    "avatar":"http://192.168.174.130:9000/eatgo/avatar/1.jpg"
  };


  aboutToAppear():void{
    let params=this
      .getUIContext()
      .getRouter()
      .getParams();

    // 根据邮箱获取用户身份信息
    getUser((params as Param).email).then((res:AxiosResponse<Result<UserInfo>>)=>{
      this.info=res.data.data;
    });
  }

  build() {
    Column(){
      TextTitleBack({title:'充值入口'})

      Image($rawfile('user/balance.svg'))
        .width(150)
        .margin({top:50})

      Text('我的余额')
        .margin({top:20})

      Text(`￥${this.info.balance}`)
        .fontSize(40)
        .fontWeight(FontWeight.Bold)
        .margin({top:20})

      Column(){
        Button('充值')
          .width('60%')
          .backgroundColor('#FF7302')
          .onClick((event: ClickEvent) => {
            this.dialogControllerTopUp.open()
          })

        Button('提现')
          .width('60%')
          .fontColor('#ff7a7a7a')
          .backgroundColor(Color.White)
          .margin({top:20})
          .onClick((event: ClickEvent) => {

          })
      }
      .margin({top:100})
    }
    .width("100%")
    .height("100%")
    .backgroundColor('#ffd7d7d7')
  }
}