import { Breadcrumb } from "antd";

export const DishView = () => {
    return (
        <>
            <Breadcrumb
                items={[
                    {
                        title: '菜品管理',
                    },
                    {
                        title: '菜品列表',
                    },
                ]}
            />
        </>
    );
}