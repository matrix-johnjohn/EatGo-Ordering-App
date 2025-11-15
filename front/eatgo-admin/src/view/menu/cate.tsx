//ANTD组件
import { Breadcrumb, Table, Image, Button, Modal } from 'antd';
import { DeleteOutlined, FormOutlined } from '@ant-design/icons';
// Hooks
import { useEffect, useState } from 'react';
import type { TableColumnsType, TableProps } from 'antd';
// api接口
import { CateList } from '../../api/request/menu';
import type { DishCategorize } from '../../api/entity/menu';


export const DishCate = () => {
    // 设置表格选中框状态
    const [selectionType, setSelectionType] = useState<'checkbox'>('checkbox');

    // 单条删除弹窗状态
    const [isModalOpen, setIsDeleteDishCateModalOpen] = useState(false);

    // 当前删除分类
    const [CurrentAboutToDeleteCateItem, setCurrentAboutToDeleteCateItem] = useState<DishCategorize | null>(null);

    // 打开弹窗
    const showDeleteDishCateModal = (item: DishCategorize) => {
        // 设置当前要删除的菜品分类
        setCurrentAboutToDeleteCateItem(item);
        console.log('删除选中', item);

        setIsDeleteDishCateModalOpen(true);
    };

    // 确认删除按钮点击触发事件
    const DeleteDishCatehandleOk = (item: DishCategorize) => {
        // 确认删除逻辑
        console.log('确认删除', item);

        setIsDeleteDishCateModalOpen(false);
    };

    // 取消删除按钮点击触发事件
    const DeleteDishCatehandleCancel = () => {
        setIsDeleteDishCateModalOpen(false);
    };

    // 表格数据
    const [CateTableData, setCateTableData] = useState<DishCategorize[]>([]);

    // 表格字段设置
    const columns: TableColumnsType<DishCategorize> = [
        {
            title: '分类名称',
            dataIndex: 'name',
        },
        {
            title: '分类Icon',
            dataIndex: 'icon',
            render: (text: string) => <img src={text} style={{ width: '100px' }} />,
        },
        {
            title: '分类轮播',
            dataIndex: 'banner',
            render: (text: string[]) => <Image.PreviewGroup
                items={text}
            >
                <Image
                    width={200}
                    src={text[0]}
                />
            </Image.PreviewGroup>,
        },
        {
            title: '创建时间',
            dataIndex: 'createTime',
            render: (text: string) => (<>
                <p>{text.split('T').join(' ')}</p>
            </>)
        },
        {
            title: '最后操作时间',
            dataIndex: 'updateTime',
            render: (text: string) => (<>
                <p>{text.split('T').join(' ')}</p>
            </>)
        },
        {
            title: '操作',
            render: (item: DishCategorize) => <>
                <Button
                    danger
                    icon={<DeleteOutlined />}
                    onClick={() => {
                        showDeleteDishCateModal(item);
                    }} />

                <Button
                    style={{ marginLeft: 10 }}
                    color="purple"
                    variant="solid"
                    icon={<FormOutlined />}
                    onClick={() => {
                        console.log('编辑', item);

                    }}
                />
            </>
        }
    ];

    // 表格批量选择函数
    const rowSelection: TableProps<DishCategorize>['rowSelection'] = {
        onChange: (selectedRowKeys: React.Key[], selectedRows: DishCategorize[]) => {
            console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
        },
        getCheckboxProps: (record: DishCategorize) => ({
            disabled: record.name === 'Disabled User', // Column configuration not to be checked
            name: record.name,
        }),
    };

    // 获取分类数据
    const getCateListData = async () => {
        const res = await CateList();
        return res;
    }

    // 挂载数据
    useEffect(() => {
        // 分类列表数据
        getCateListData().then(({ data }) => {
            let list = data.map((item: DishCategorize, index: number) => {
                return {
                    ...item,
                    banner: JSON.parse(item.banner as unknown as string),
                    key: item.id
                }
            })
            setCateTableData(list);
        })
    }, []);
    return (
        <>
            <Breadcrumb
                items={[
                    {
                        title: '菜品管理',
                    },
                    {
                        title: '分类管理',
                    },
                ]}
            />

            {/*表格数据*/}
            <Modal
                title="系统提示"
                closable={{ 'aria-label': 'Custom Close Button' }}
                open={isModalOpen}
                onOk={() => { DeleteDishCatehandleOk(CurrentAboutToDeleteCateItem as DishCategorize) }}
                onCancel={DeleteDishCatehandleCancel}
            >
                {CurrentAboutToDeleteCateItem ? (
                    <p>
                        删除
                        <span style={{ color: '#6b21a8' }}>{CurrentAboutToDeleteCateItem.name}</span>
                        后，
                        <span style={{ color: '#6b21a8' }}>{CurrentAboutToDeleteCateItem.name}</span>
                        下的所有标签和菜品都将会被删除，继续删除？
                    </p>
                ) : (
                    <p>加载中...</p>
                )}
            </Modal>
            <Table<DishCategorize>
                rowSelection={{ type: selectionType, ...rowSelection }}
                columns={columns}
                dataSource={CateTableData}
                pagination={{
                    pageSize: 5, // 每页显示多少条
                    showSizeChanger: true, // 是否显示“每页几条”的下拉框
                    showQuickJumper: true, // 是否显示“跳转到某页”输入框
                    total: CateTableData.length, // 总数据量（如果后端分页则用实际总数）
                }}
            />
        </>
    );
}