export interface comment{
  "id": number,
  "comment": string,
  "userId": number,
  "dishId": number,
  "avatar": string,
  "username": string,
  "createTime": string,
  "updateTime": string
}

export interface CommentQuery{
  "comment":string,
  "userId":number,
  "dishId":number
}