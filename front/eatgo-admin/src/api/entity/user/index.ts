export interface UserQuery {
    email: string;
}

export interface User {
    "key": number,
    "id": number,
    "username": string,
    "password": string,
    "email": string,
    "balance": number,
    "isEffective": number,
    "auth": number,
    "avatar": string,
    "createTime": string,
    "updateTime": string
}