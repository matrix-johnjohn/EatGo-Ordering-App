import notificationManager from '@ohos.notificationManager';

interface TabClass{
  text:string;
  icon:Resource;
}

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
          TabContent(){
            Text(item.text)
          }
          .tabBar(this.tabBuilder(item,index))
          .backgroundColor('#ffefefef')
          .expandSafeArea([SafeAreaType.SYSTEM], [SafeAreaEdge.TOP, SafeAreaEdge.BOTTOM])
        })
      }
      .onChange((index:number)=>{
        this.currentIndex=index;
      })
      .backgroundColor('#fff6f6f6')
    }

    .onReady((context: NavDestinationContext) => {
      this.pathStack = context.pathStack;
    })
  }
}