import { HeaderCard } from '../../component/user/user';

@Component
export struct User {

  build() {
    Column(){
      HeaderCard({
        username:'matrix',
        avatar:'https://avatars.githubusercontent.com/u/192420957?s=400&u=43db1ec1ca0ffe7c2bd852282f5ce48c524ccde0&v=4',
        email:'1596903229@qq.com'
      })


    }
    .width('100%')
    .height('100%')
  }
}