import { Breadcrumb } from "antd";

export const Order = () => {
    return (
        <>
            <Breadcrumb
                items={[
                    {
                        title: '订单管理',
                    },
                    {
                        title: '订单列表',
                    },
                ]}
            />

            <div>

            </div>
        </>
    );
}