import { useEffect, useState } from "react";
// 路由
import { Outlet } from "react-router";
import { useNavigate } from "react-router-dom";
// 静态资源
import Logo from '../../assets/logo.png';
// ANTD
import { Layout } from 'antd';
const { Header, Sider, Content } = Layout;
import { Button } from 'antd';
import { AppstoreOutlined } from '@ant-design/icons';
import type { MenuProps } from 'antd';
import { Menu } from 'antd';
type MenuItem = Required<MenuProps>['items'][number];
// Style
import '../../style/layout/layout.css';
// 配置文件
import { PROJECT_TITLE, LOGIN_EXIT } from '../../config';

import { SelectMenuItemStore } from '../../store/index';
// 导航栏数据,Todo:待封装
const items: MenuItem[] = [
    {
        key: 'user',
        label: '用户管理',
        icon: <AppstoreOutlined />,
        children: [
            { key: '/user/index', icon: <AppstoreOutlined />, label: '用户列表' },
            { key: '/user/chat', icon: <AppstoreOutlined />, label: '用户聊天' }
        ],
    },
    {
        type: 'divider',
    },
    {
        key: 'menu',
        label: '菜单管理',
        icon: <AppstoreOutlined />,
        children: [
            { key: '/menu/cate', icon: <AppstoreOutlined />, label: '分类管理' },
            { key: '/menu/tag', icon: <AppstoreOutlined />, label: '标签管理' },
            { key: '/menu/dish', icon: <AppstoreOutlined />, label: '菜品管理' }
        ],
    },
    {
        type: 'divider',
    },
    {
        key: 'order',
        label: '订单管理',
        icon: <AppstoreOutlined />,
        children: [
            { key: '/order/index', icon: <AppstoreOutlined />, label: '订单管理' }
        ]
    }, {
        type: 'divider',
    }
];
// 样式
const headerStyle: React.CSSProperties = {
    color: '#fff',
    height: 100,
    lineHeight: '100px',
    backgroundColor: '#323232',
};
const HeaderContentLayout: React.CSSProperties = {
    width: '100%',
    display: 'flex',
    justifyContent: 'space-between'
};
const HeaderLayout: React.CSSProperties = {
    display: 'flex',
    justifyItems: 'center'
};
const LogoStyle: React.CSSProperties = {
    width: 80,
    height: 80,
    marginTop: 10,
    marginBottom: 10,
    marginRight: 10,
    borderRadius: 10
};

export const Main: React.FC = () => {
    // 调用本地缓存
    const { MenuItem, setMenuItem } = SelectMenuItemStore();
    // 侧边栏拉伸状态
    const [collapsed, setCollapsed] = useState(false);
    // 拉伸侧边栏
    const toggleCollapsed = () => {
        setCollapsed(!collapsed);
    };

    useEffect(() => {
        navigate(MenuItem as string);
    }, []);
    // 侧边栏选择
    const selectedAsideItem: MenuProps['onClick'] = (e) => {
        let path = e.key;

        // 设置当前导航item
        setMenuItem(path);

        // 跳转页面
        navigate(path);
    };
    // 路由实例
    const navigate = useNavigate();
    return (
        <>
            <Layout>
                <Header style={headerStyle}>
                    <div style={HeaderContentLayout}>
                        <div style={HeaderLayout}>
                            <img style={LogoStyle} src={Logo} alt="" onClick={toggleCollapsed} />
                            <span style={{ fontSize: 30 }}>{PROJECT_TITLE}</span>
                        </div>
                        <div>
                            <Button
                                size='large'
                                onClick={() => {
                                    // Todo:登录逻辑
                                    navigate('/login');
                                }}
                            >
                                {LOGIN_EXIT}
                            </Button>
                        </div>
                    </div>

                </Header>
                <Layout>
                    <Sider width={!collapsed ? "25%" : "5%"} color="#fff" style={{ backgroundColor: 'white' }}>
                        <Menu
                            onClick={selectedAsideItem}
                            style={{ width: '100%' }}
                            defaultSelectedKeys={[MenuItem as string]}
                            defaultOpenKeys={['user']}
                            mode="inline"
                            inlineCollapsed={collapsed}
                            items={items}
                        />
                    </Sider>
                    <Content style={{ height: '100%', padding: 20 }}>
                        <Outlet />
                    </Content>
                </Layout>
            </Layout>
        </>
    );
}