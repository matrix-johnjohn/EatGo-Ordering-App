// 跳转页面入口函数
@Builder
export function HomeBuilder() {
  Home();
};

@Builder
function Title(){
  Row(){
    Text('Home').width('100%').textAlign(TextAlign.Center)
  }.width('100%')
}

@Component
struct Home {
  pathStack: NavPathStack = new NavPathStack();//控制跳转的对象

  build() {
    NavDestination(){
      Text('Home').onClick(()=>{
        this.pathStack.pushPathByName("Layout",null,false);
      })
    }
    .title(Title())
    .onReady((context: NavDestinationContext) => {
      this.pathStack = context.pathStack;
    })
  }
}