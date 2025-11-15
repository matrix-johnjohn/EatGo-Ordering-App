import { createBrowserRouter } from "react-router";

import { Login } from "../view/login";
import { Main } from "../view/layout";
// User
import { UserPage } from "../view/user/index";
import { Chat } from "../view/user/chat";
// Menu
import { DishView } from "../view/menu/dish";
import { DishTag } from "../view/menu/tag";
import { DishCate } from "../view/menu/cate";
// Order
import { Order } from "../view/order/index";
// 报错页面
import { Error } from "../view/error/404";

export const router = createBrowserRouter([

    {
        path: '/',
        Component: Main,
        children: [
            {
                path: '/',
                Component: UserPage
            },
            {
                path: '/user/index',
                Component: UserPage
            },
            {
                path: '/user/chat',
                Component: Chat
            },
            {
                path: '/menu/cate',
                Component: DishCate
            },
            {
                path: '/menu/tag',
                Component: DishTag
            },
            {
                path: '/menu/dish',
                Component: DishView
            },
            {
                path: '/order/index',
                Component: Order
            }
        ]
    },
    {
        path: '/login',
        Component: Login
    },
    {
        path: '*',
        Component: Error
    }
]);

