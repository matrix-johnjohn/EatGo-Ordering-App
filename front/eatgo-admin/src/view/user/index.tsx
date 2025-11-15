import { Breadcrumb, Button, message, notification } from 'antd';
import { Divider, Table, Tag, Image } from 'antd';
import type { TableColumnsType, TableProps } from 'antd';
import { useEffect, useState } from 'react';

import type { User } from '../../api/entity/user/index'
import { updateUserEffective, userList } from '../../api/request/user';

const rowSelection: TableProps<User>['rowSelection'] = {
    onChange: (selectedRowKeys: React.Key[], selectedRows: User[]) => {
        console.log(selectedRowKeys);

        console.log(selectedRows);
    }
};

export const UserPage: React.FC = () => {
    const [selectionType] = useState<'checkbox'>('checkbox');

    const [api, contextHolder] = notification.useNotification();

    // 表格数据
    const [data, setData] = useState<User[]>([]);

    // 表格字段
    const fields: TableColumnsType<User> = [
        {
            title: '用户名',
            dataIndex: 'username',
            width: 150
        },
        {
            title: '邮箱',
            dataIndex: 'email',
            width: 200
        },
        {
            title: '密码',
            dataIndex: 'password',
            width: 180
        },
        {
            title: '头像',
            dataIndex: 'avatar',
            width: 120,
            render: (text) => (<Image
                width={80}
                src={text}
            />),
        },
        {
            title: '余额',
            dataIndex: 'balance',
            width: 120,
            fixed: 'right'
        },
        {
            title: '用户状态',
            dataIndex: 'isEffective',
            width: 120,
            render: (tag: number) => (
                <span>

                    <Tag color={`${tag ? 'blue' : 'pink'}`} key={tag}>
                        {tag ? '正常' : '冻结'}
                    </Tag>
                </span>
            ),
        },
        {
            title: '用户权限',
            dataIndex: 'auth',
            width: 150,
            fixed: 'right',
            render: (tag: number) => (
                <span>
                    <Tag color={`${tag ? 'blue' : 'green'}`} key={tag}>
                        {tag ? '超级管理员' : '用户'}
                    </Tag>
                </span>
            ),
        },
        {
            title: '创建时间',
            dataIndex: 'createTime',
            width: 250,
            render: (item: string) => (
                <p>{item.split('T').join(' ')}</p>
            )
        },
        {
            title: '最后修改时间',
            dataIndex: 'updateTime',
            width: 250,
            render: (item: string) => (
                <p>{item.split('T').join(' ')}</p>
            )
        },
        {
            title: '操作',
            width: 100,
            fixed: 'right',
            render: (item: User) => (

                <div>
                    <Button color={item.isEffective ? "danger" : 'primary'} variant="solid" onClick={async () => {
                        console.log(item.username);

                        await updateUserEffective(item).then((res) => {
                            console.log(res.data.data);
                        })

                        setData(prev =>
                            prev.map(user =>
                                user.id === item.id
                                    ? { ...user, isEffective: item.isEffective ? 0 : 1 }
                                    : user
                            )
                        );

                        api.info({
                            message: `用户信息更新成功`,
                            description: `${item.username} ${item.isEffective === 1 ? '冻结' : '恢复'}`,
                        });
                    }}>
                        {item.isEffective ? '冻结' : '恢复'}
                    </Button>
                </div>
            )
        },
    ];

    useEffect(() => {
        userList().then((res) => {
            const processedData = res.data.data.map((item: User) => ({
                ...item,
                key: item.id,
            }));
            setData(processedData);
        })
    }, [])
    return (
        <>
            {contextHolder} {/* 👈 必须渲染！ */}
            <Breadcrumb
                items={[
                    {
                        title: '用户管理',
                    },
                    {
                        title: '用户列表',
                    },
                ]}
            />

            <div>
                <Divider />
                <Table<User>
                    rowSelection={{ type: selectionType, ...rowSelection }}
                    columns={fields}
                    dataSource={data}
                    scroll={{ x: '1500' }}
                    pagination={{
                        pageSize: 5, // 每页显示多少条
                        showSizeChanger: true, // 是否显示“每页几条”的下拉框
                        showQuickJumper: true, // 是否显示“跳转到某页”输入框
                        total: data.length, // 总数据量（如果后端分页则用实际总数）
                    }}
                />
            </div>
        </>
    )
};