import { AxiosResponse } from '@ohos/axios'
import { UserInfo } from '../../../api/entity/user'
import { Result } from '../../../api/request/base'
import { editUser, getUser } from '../../../api/request/user'
import { TextTitleBack } from '../../../component/common/CommonTitle'
import { preferences } from '@kit.ArkData'
import  { picker } from '@kit.CoreFileKit';
import { common } from '@kit.AbilityKit'

@Entry
@Component
struct edit{

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

  aboutToAppear(): void {

    let email=preferences.getPreferencesSync(this.getUIContext().getHostContext(),{name:"user"}).getSync("email","");

    getUser(email as string).then((res:AxiosResponse<Result<UserInfo>>)=>{
      this.info=res.data.data;
      console.log('edit页面',JSON.stringify(res.data.data));
    })
  }
  build() {
    Column(){
      TextTitleBack({title:"个人信息"})
      // 卡片栏
      Column(){
        Row(){
          Text('头像')
            .margin({left:5})

          Column().layoutWeight(1)

          Image(this.info.avatar)
            .width(50)
            .margin({right:5})
            .onClick(()=>{
              let documentSelectOptions = new picker.DocumentSelectOptions();

              let documentPicker=new picker.DocumentViewPicker(this.getUIContext().getHostContext() as common.UIAbilityContext);

              documentPicker.select(documentSelectOptions).then((documentSelectResult: Array<string>)=>{
                let p=(documentSelectResult[0].split('/')[documentSelectResult[0].split('/').length-1]);

                let arr:string[]=this.info.avatar.split('/');

                let res=arr.slice(0,arr.length-1).join("/")+"/"+p;

                this.info.avatar=res;
              })
            })
        }
        .width('100%')
        .height(60)
        .margin({top:10})
        .backgroundColor(Color.White)

        Row(){
          Text('昵称')
            .margin({left:5})

          TextInput({text:this.info.username})
            .backgroundColor(Color.Transparent)
            .width('40%')
            .onChange((e)=>{
              this.info.username=e;
            })
        }
        .width('100%')
        .height(60)
        .margin({top:10})
        .justifyContent(FlexAlign.SpaceBetween)
        .backgroundColor(Color.White)

        Row(){
          Text('邮箱')
            .margin({left:5})

          TextInput({text:this.info.email})
            .backgroundColor(Color.Transparent)
            .width('40%')
            .onChange((e)=>{
              this.info.email=e;
            })
        }
        .width('100%')
        .height(60)
        .margin({top:10})
        .justifyContent(FlexAlign.SpaceBetween)
        .backgroundColor(Color.White)

        Row(){
          Text('密码')
            .margin({left:5})

          TextInput({text:this.info.password})
            .backgroundColor(Color.Transparent)
            .type(InputType.Password)
            .width('40%')
            .onChange((e)=>{
              this.info.password=e;
            })
        }
        .width('100%')
        .height(60)
        .margin({top:10})
        .justifyContent(FlexAlign.SpaceBetween)
        .backgroundColor(Color.White)
      }
      .width('100%')
      .margin({top:20})

      // 按钮
      Button('修改身份信息')
        .fontSize(15)
        .width('60%')
        .height(40)
        .backgroundColor('#FC9D2F')
        .borderRadius(10)
        .margin({top:100})
        .onClick((event: ClickEvent) => {
          console.log(JSON.stringify(this.info))
          editUser(this.info).then(()=>{
            console.log("success");
          })
        })
    }
    .width('100%')
    .height('100%')
    .expandSafeArea([SafeAreaType.SYSTEM], [SafeAreaEdge.TOP, SafeAreaEdge.BOTTOM])
    .backgroundColor('#ffefefef')
  }
}