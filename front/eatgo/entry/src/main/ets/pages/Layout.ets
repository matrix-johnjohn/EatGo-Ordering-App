import { TabClass } from '../utils/layout/index';
import { Home } from './home/home';
import { Order } from './order/order';
import { Chat } from './chat/chat';
import { User } from './user/user';
import { preferences, relationalStore } from '@kit.ArkData';
import { verificationToken } from '../api/request/user';
import { AxiosResponse } from '@ohos/axios';
import { Result } from '../api/request/base';

@Builder
export function LayoutBuilder() {
  Layout();
}

@Component
struct Layout {

  @State currentIndex:number=0;

  pathStack: NavPathStack = new NavPathStack();//控制跳转的对象

  tableData:TabClass[]=[//底部导航栏数据
    {text:'首页',icon:$rawfile('image/home/home.svg')},
    {text:'订单',icon:$rawfile('image/order/order.svg')},
    {text:'聊天',icon:$rawfile('image/chat/chat.svg')},
    {text:'我的',icon:$rawfile('image/user/user.svg')},
  ];

  @Builder tabBuilder(item:TabClass,index:number){
    Column({space:5}){
      Image(item.icon)
        .width(24)
        .fillColor(this.currentIndex===index?'#FFFF7302':'#ff737373')
      Text(item.text)
        .fontSize(14)
        .fontColor(this.currentIndex===index?'#FFFF7302':'#ff222222')
    }
  };

  build() {
    NavDestination(){
      Tabs({barPosition:BarPosition.End}){
        ForEach((this.tableData),(item:TabClass,index:number)=>{
            if (index===0){
              TabContent(){
                Home()
              }.tabBar(this.tabBuilder(item,index))
              .backgroundColor('#ffefefef')
              .expandSafeArea([SafeAreaType.SYSTEM], [SafeAreaEdge.TOP, SafeAreaEdge.BOTTOM])

            }else if(index===1){
              TabContent(){
                Order({num:this.currentIndex})
              }.tabBar(this.tabBuilder(item,index))
              .backgroundColor('#ffefefef')
              .expandSafeArea([SafeAreaType.SYSTEM], [SafeAreaEdge.TOP, SafeAreaEdge.BOTTOM])
            }else if(index===2){
              TabContent(){
                Chat()
              }.tabBar(this.tabBuilder(item,index))
              .backgroundColor('#ffefefef')
              .expandSafeArea([SafeAreaType.SYSTEM], [SafeAreaEdge.TOP, SafeAreaEdge.BOTTOM])
            }else if(index===3){
              TabContent(){
                User({num:this.currentIndex})
              }.tabBar(this.tabBuilder(item,index))
              .backgroundColor('#ffefefef')
              .expandSafeArea([SafeAreaType.SYSTEM], [SafeAreaEdge.TOP, SafeAreaEdge.BOTTOM])
            }
        })
      }
      .onChange((index:number)=>{
        this.currentIndex=index;

        console.log(index.toString())
      })
      .backgroundColor('#fff6f6f6')
    }
    .onReady((context: NavDestinationContext) => {
      this.pathStack = context.pathStack;

      let sync=preferences
        .getPreferencesSync(this.getUIContext().getHostContext(),{name:"user"});

      // 从磁盘中读取email
      let email=sync.getSync('email',0);

      // 从磁盘中读取token
      let token=sync.getSync('token',0);

      //token是否为空
      if(!token){
        this.pathStack.pushPathByName("Login",email,false);
      }

      //token是否合法
      verificationToken(token as string,email as string).then((res:AxiosResponse<Result<boolean>>)=>{
        let f=res.data.data;
        if(f===null){
          this.pathStack.pushPathByName("Login",email,false);
        }
      })
    })
    .hideTitleBar(true)
  }
}