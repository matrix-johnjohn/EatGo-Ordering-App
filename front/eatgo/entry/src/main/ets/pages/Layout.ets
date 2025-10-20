// 跳转页面入口函数
@Builder
export function LayoutBuilder() {
  Layout();
}

@Component
struct Layout {
  pathStack: NavPathStack = new NavPathStack();//控制跳转的对象

  build() {
    NavDestination(){
    }
    .title('Layout')
    .onReady((context: NavDestinationContext) => {
      this.pathStack = context.pathStack;
    })
  }
}