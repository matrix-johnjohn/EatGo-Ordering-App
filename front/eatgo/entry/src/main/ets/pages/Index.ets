@Entry
@Component
struct Index {
  pageStack: NavPathStack=new NavPathStack();
  build() {
    Navigation(this.pageStack)
    .onAppear(()=>{
      this.pageStack.pushPathByName("Layout",null,false);
    })
    .hideNavBar(true)
  }

}