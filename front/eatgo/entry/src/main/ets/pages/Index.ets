@Entry
@Component
struct Index {
  pageStack: NavPathStack=new NavPathStack();
  build() {
    Navigation(this.pageStack) {
    }
    .onAppear(()=>{
      this.pageStack.pushPathByName("Home",null,false);
    })
    .hideNavBar(true)
  }

}