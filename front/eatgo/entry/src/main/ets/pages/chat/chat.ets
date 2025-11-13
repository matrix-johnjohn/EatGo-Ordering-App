import { TextTitle } from '../../component/common/CommonTitle';
import { webSocket } from '@kit.NetworkKit';
import { BusinessError } from '@kit.BasicServicesKit';
import { promptAction } from '@kit.ArkUI';
import { UserInfo } from '../../api/entity/user';
import { Result } from '../../api/request/base'
import { getUser } from '../../api/request/user';
import { preferences } from '@kit.ArkData';
import { AxiosResponse } from '@ohos/axios';
import { ChatMessageQuery } from '../../api/entity/chat';
import { historyList } from '../../api/request/chat/chat';

@Component
export struct Chat{

  //当前用户信息
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
  //聊天上传数据
  @State chatData:ChatMessageQuery= {
    from: 0,
    to: 1,
    message: ''
  };
  //历史记录
  @State chatHistory:ChatMessageQuery[]=[];

  private socket:webSocket.WebSocket=webSocket.createWebSocket();

  async connectWebSocket(){
    let email=preferences.getPreferencesSync(this.getUIContext().getHostContext(),{name:"user"}).getSync("email","") as string;
    await getUser(email)
      .then((res:AxiosResponse<Result<UserInfo>>)=>{
        this.info=res.data.data;
        this.chatData.from=res.data.data.id;
      })

    await historyList({from:1,to:this.info.id,message:''})
      .then((result:AxiosResponse<Result<ChatMessageQuery[]>>)=>{

        console.log(JSON.stringify(result.data.data))
        this.chatHistory=result.data.data;
      })
      .catch(()=>{
        console.log('error')
      })
    // 建立连接
    this.socket.connect(`ws://192.168.174.1:8080/ws?userId=${this.info.id}`,(err: BusinessError, value: boolean) => {
      if(!err){
        promptAction.openToast({
          message:"建立连接成功",
          duration:2000
        })
        console.info("Message send successfully");
      }else{
        console.error("Failed to send the message. Err:" + JSON.stringify(err));
      }
    });
    //监听客户端打开连接
    this.socket.on("open", (err: BusinessError, value: Object)=>{
      if(!err){
        promptAction.openToast({
          message:"打开连接成功",
          duration:2000
        })
        console.info("Message send successfully");
      }else{
        console.error("Failed to send the message. Err:" + JSON.stringify(err));
      }
    })
    //接收消息
    this.socket.on('message', (err: BusinessError, value: string | ArrayBuffer) => {
      if(!err){
        promptAction.openToast({
          message:"接收信息成功",
          duration:2000
        })
        let v=value.toString();

        if(v!=='欢迎连接 WebSocket 服务器！'){
          console.log('v',v)

          let q=JSON.parse(v) as ChatMessageQuery;

          this.chatHistory.push(q);
        }

      }
    });
  }

  aboutToAppear():void {
    this.connectWebSocket();
  }

  build() {
    Column(){
      TextTitle({title:'聊天'});
      //聊天记录
      List(){
        ForEach(this.chatHistory,(item:ChatMessageQuery)=>{
          ListItem(){
            Row(){
              if (item.from===this.info.id) {
                Column().layoutWeight(100)
              }
              if(this.info.id!==item.from) {
                Column(){
                  Text('商家')
                    .fontSize(15)
                    .fontColor(Color.Gray)
                  Image($r('app.media.logo'))
                    .width(50)
                    .borderRadius(50)
                    .margin({right:5})
                }
              }
              Text(item.message)
                .width('60%')
                .padding(15)
                .backgroundColor(item.from===this.info.id?Color.Orange:Color.White)
                .borderRadius(10)
                .fontColor(item.from===this.info.id?Color.White:Color.Black)
                .margin({top:5})

              if(this.info.id===item.from) {
                Column(){
                  Text(this.info.username)
                    .fontSize(15)
                    .fontColor(Color.Gray)
                  Image(this.info.avatar)
                    .width(50)
                    .borderRadius(50)
                    .margin({left:5})
                }
              }
            }
            .width('100%')
          }
          .margin({top:10,bottom:10})
        })
      }
      .width('100%')
      .height('80%')
      .padding(10)
      .expandSafeArea([SafeAreaType.KEYBOARD,SafeAreaType.SYSTEM],[SafeAreaEdge.BOTTOM])
      //发送信息
      Row(){
        TextInput({text:this.chatData.message})
          .width('70%')
          .onChange((e)=>{
            this.chatData.message=e;
          })
        Button('发送消息')
          .width('30%')
          .margin({left:10})
          .onClick(() => {
            this.socket.send(JSON.stringify(this.chatData))
              .then(()=>{
                console.log(JSON.stringify(this.chatData));
                let chatData:ChatMessageQuery= {
                  from: 0,
                  to: 0,
                  message: ''
                };
                chatData.from=this.chatData.to;
                chatData.from=this.chatData.from;
                chatData.message=this.chatData.message;
                this.chatHistory.push(chatData);

                this.chatData.message='';
              })
              .catch((err:BusinessError)=>{
                console.log('error');
              });
          })
      }
      .width('100%')
      .padding(10)

    }
    .width('100%')
    .height('100%')
  }
}