import {
  Breadcrumb,
  Button,
  Form,
  Input,
  notification,
  Select,
  Table,
  type TableColumnsType,
} from "antd";
import { useEffect, useState } from "react";
import {
  orderReady,
  orderTableList,
  searchOrderList,
} from "../../api/request/order";
import type { OrderTable } from "../../api/entity/order";
import type { TableRowSelection } from "antd/es/table/interface";
import { Image } from "antd";
import { TableComplexButton } from "../../component/order";
import { useForm } from "antd/es/form/Form";

export const Order = () => {
  // 消息提示
  const [api, contextHolder] = notification.useNotification();

  // 分页数据
  const [current, setCurrent] = useState<number>(() => {
    return localStorage.getItem("orderPage");
  });
  // 订单列表表格数据
  const [orderTable, setOrderTable] = useState<OrderTable[]>();

  const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]);

  const columns: TableColumnsType<OrderTable> = [
    { title: "点餐用户", dataIndex: "username" },
    { title: "菜品名称", dataIndex: "dishName" },
    {
      title: "菜品图片",
      dataIndex: "dishImg",
      render(value: string) {
        return (
          <>
            <Image width={120} src={value} />
          </>
        );
      },
    },
    { title: "菜品描述", dataIndex: "dishDesc" },
    { title: "购买数量", dataIndex: "count" },
    { title: "单价", dataIndex: "price" },
    { title: "总价", dataIndex: "totalPrice" },
    {
      title: "订单创建时间",
      dataIndex: "createTime",
      render(value: string) {
        return (
          <>
            <p>{value.split("T").join(" ")}</p>
          </>
        );
      },
    },
    {
      title: "订单出餐时间",
      dataIndex: "updateTime",
      render(value: string) {
        return (
          <>
            <p>{value.split("T").join(" ")}</p>
          </>
        );
      },
    },
    {
      title: "订单操作",
      render(value: OrderTable) {
        return (
          <>
            {TableComplexButton(value.status, () => {
              orderReady(value.id).then((res) => {
                console.log(res);
                api.open({
                  type: "success",
                  message: res.message,
                  description: res.data,
                  showProgress: true,
                  pauseOnHover: true,
                  onClose() {
                    window.location.reload();
                  },
                });
              });
            })}
          </>
        );
      },
    },
  ];

  const onSelectChange = (newSelectedRowKeys: React.Key[]) => {
    console.log("selectedRowKeys changed: ", newSelectedRowKeys);
    setSelectedRowKeys(newSelectedRowKeys);
  };
  const rowSelection: TableRowSelection<OrderTable> = {
    selectedRowKeys,
    onChange: onSelectChange,
  };
  // 搜索表单
  const [selectedOrderStatusForm] = useForm();
  /**
   * Function:挂载函数
   */
  useEffect(() => {
    //订单数据
    orderTableList().then((res) => {
      console.log(res.data);

      const result = res.data.map((item) => {
        return { ...item, key: item.id };
      });

      setOrderTable(result);
    });
  }, []);
  return (
    <>
      {contextHolder}
      <Breadcrumb
        items={[
          {
            title: "订单管理",
          },
          {
            title: "订单列表",
          },
        ]}
      />
      {/**表单 */}
      <div
        style={{
          marginTop: 20,
          display: "flex",
          justifyContent: "space-between",
        }}
      >
        <Form form={selectedOrderStatusForm} style={{ display: "flex" }}>
          <Form.Item layout="vertical" name={"user"} label="用户账号">
            <Input
              placeholder="用户账号搜索"
              onChange={(e) => {
                console.log(e.target.value);
              }}
            />
          </Form.Item>
          <Form.Item
            style={{ marginLeft: "15px" }}
            layout="vertical"
            name={"dish"}
            label="菜品"
          >
            <Input
              placeholder="菜品搜索"
              onChange={(e) => {
                console.log(e.target.value);
              }}
            />
          </Form.Item>
          <Form.Item
            style={{ marginLeft: 15 }}
            layout="vertical"
            label="订单状态"
            name={"status"}
          >
            <Select
              style={{ width: "150px" }}
              placeholder={"订单状态选择"}
              onChange={(e) => {
                console.log(e);
                searchOrderList(e).then((res) => {
                  console.log(res.data);
                  const items = res.data.map((item) => {
                    return { ...item, key: item.id };
                  });
                  setOrderTable(items);
                });
              }}
              options={[
                { value: "0", label: <span>制作中</span> },
                { value: "1", label: <span>已出餐</span> },
                { value: "2", label: <span>已完成</span> },
                { value: "101", label: <span>已取消</span> },
              ]}
            />
          </Form.Item>
        </Form>

        <Button
          onClick={() => {
            const user = selectedOrderStatusForm.getFieldValue("user") || "";
            const dish = selectedOrderStatusForm.getFieldValue("dish") || "";

            setOrderTable((prev) => {
              const source = prev ?? [];

              let filtered = source;

              if (user.trim()) {
                filtered = filtered.filter((item) =>
                  item.username?.includes(user.trim())
                );
              }

              if (dish.trim()) {
                filtered = filtered.filter((item) =>
                  item.dishName?.includes(dish.trim())
                );
              }

              return filtered;
            });
          }}
        >
          点击搜索
        </Button>
      </div>
      {/**表格 */}
      <div style={{ marginTop: 20 }}>
        <Table<OrderTable>
          rowSelection={rowSelection}
          columns={columns}
          dataSource={orderTable}
          pagination={{
            onChange(page, pageSize) {
              localStorage.setItem("orderPage", page);
              setCurrent(page);
            },
            current,
            pageSize: 5,
          }}
        />
      </div>
    </>
  );
};
