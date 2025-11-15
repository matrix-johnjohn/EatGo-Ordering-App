import { Breadcrumb } from "antd";

export const DishTag = () => {
    return (
        <>
            <Breadcrumb
                items={[
                    {
                        title: '菜品管理',
                    },
                    {
                        title: '标签管理',
                    },
                ]}
            />
        </>
    );
}