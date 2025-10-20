import { PersistenceV2, router } from '@kit.ArkUI';

@Entry
@Component
struct Index {
  @State message: string = 'Hell';

  aboutToAppear(): void {
    if(PersistentStorage.keys().indexOf('token')===-1){
      router.push({url:'pages/login/Login'})
    }else{
      console.log('已经登录',AppStorage.get('token'))
    }
  }

  build() {
    Tabs({barPosition:BarPosition.End}){

    }
  }
}