import { Breadcrumb } from 'antd';
export const Chat = () => {
    return (
        <>
            <Breadcrumb
                items={[
                    {
                        title: '用户管理',
                    },
                    {
                        title: '用户聊天',
                    },
                ]}
            />
        </>
    )
};