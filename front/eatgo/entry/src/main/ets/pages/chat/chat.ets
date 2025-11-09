import { ws } from '../../api/request/chat';
import { TextTitle } from '../../component/common/CommonTitle';
import { webSocket } from '@kit.NetworkKit';
import { BusinessError } from '@kit.BasicServicesKit';
import { promptAction } from '@kit.ArkUI';

let obj=JSON.stringify({ "from":15, "to":1, "message":"发送信息" });

@Component
export struct Chat{

  connectWebSocket(){
    const ws=webSocket.createWebSocket();

    // 建立连接
    ws.connect("ws://192.168.174.1:8080/ws?userId=15",(err: BusinessError, value: boolean) => {
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
    ws.on("open", (err: BusinessError, value: Object)=>{
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
    ws.on('message', (err: BusinessError, value: string | ArrayBuffer) => {
      if(!err){
        promptAction.openToast({
          message:"接收信息成功",
          duration:2000
        })
        console.info(JSON.stringify(value));
      }
    });
  }

  aboutToAppear(): void {
    this.connectWebSocket()
  }

  build() {
    Column(){
      TextTitle({title:'聊天'});

      Button('发送消息').onClick(() => {
        ws.send(obj)
          .then((res)=>{
            console.log(JSON.stringify(res))
          })
          .catch((err:BusinessError)=>{

          });
      })

    }
    .width('100%')
    .height('100%')
  }
}