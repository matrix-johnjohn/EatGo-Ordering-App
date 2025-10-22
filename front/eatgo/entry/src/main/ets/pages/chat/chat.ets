import { TextTitle } from '../../component/common/CommonTitle';

@Component
export struct Chat{

  build() {
    Column(){
      TextTitle({title:'聊天'})
    }
    .width('100%')
    .height('100%')
  }
}