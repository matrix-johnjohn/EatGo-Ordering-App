import {
  Breadcrumb,
  Button,
  Flex,
  Form,
  Input,
  Modal,
  notification,
  Select,
  Table,
  Tag,
  Tooltip,
  type TableColumnsType,
} from "antd";
import { SearchOutlined } from "@ant-design/icons";
import type { DishTagVo } from "../../api/entity/menu";
import { useEffect, useState } from "react";
import {
  BatchRemoveDishTag,
  CateList,
  insertDishTag,
  removeDishTag,
  searchTagList,
  tagAllList,
  updateDishTag,
} from "../../api/request/menu";

export const DishTag = () => {
  /**
   * Data:分页数据
   */
  const [current, setCurrentPage] = useState<number>(() => {
    const tagPage = localStorage.getItem("tagPage");

    return tagPage;
  });
  /**
   * Component:消息提示
   */
  const [api, contextHolder] = notification.useNotification();
  /**
   * Function:标签列表搜索
   */
  const [searchTagName, setSearchTagName] = useState<string>(""); // 标签名搜索

  const [currentCateId, setCurrentCateId] = useState<number>(); // 分类名搜索

  const [searchCate, setSearchCate] = useState<
    { value: number; label: string }[]
  >([]); // 搜索分类

  /**
   * Function:表格状态设置
   *
   * */
  const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]); // 当前选中数据

  const TagFields: TableColumnsType<DishTagVo> = [
    {
      title: "菜品标签",
      dataIndex: "name",
    },
    {
      title: "所属分类",
      dataIndex: "cateName",
      render: (cateName: string) => (
        <>
          <Tag color="volcano">{cateName}</Tag>
        </>
      ),
    },
    {
      title: "创建时间",
      dataIndex: "createTime",
      render: (time: string) => (
        <>
          <p>{time.split("T").join(" ")}</p>
        </>
      ),
    },
    {
      title: "最后更新时间",
      dataIndex: "updateTime",
      render: (time: string) => (
        <>
          <p>{time.split("T").join(" ")}</p>
        </>
      ),
    },
    {
      title: "操作",
      width: 200,
      render: (item: DishTagVo) => (
        <>
          <Flex justify="space-evenly">
            <Button
              color="danger"
              variant="solid"
              onClick={() => {
                showDeleteTagModal(item.id);
              }}
            >
              删除
            </Button>
            <Button
              color="purple"
              variant="solid"
              onClick={() => {
                showUpdateTagModal(item);
              }}
            >
              编辑
            </Button>
          </Flex>
        </>
      ),
    },
  ]; //表格字段配置

  const [TagDataSource, setTagDataSource] = useState<DishTagVo[]>([]); //表格数据

  const TagOnSelectChange = (newSelectedRowKeys: React.Key[]) => {
    console.log("当前选中", newSelectedRowKeys);
    setSelectedRowKeys(newSelectedRowKeys);
  }; //表格选择事件

  /**
   * Function:新增标签
   */
  const [InsertTag] = Form.useForm();

  const [isAddTagModalOpen, setIsAddTagModalOpen] = useState(false);

  const showAddTagModal = () => {
    setIsAddTagModalOpen(true);
  };

  const handleAddTagOk = () => {
    const name: string = InsertTag.getFieldValue("name") as string;
    const cate: number = InsertTag.getFieldValue("cate") as number;

    insertDishTag(name, cate).then((res) => {
      // 先执行完
      api.open({
        type: "success",
        message: "消息提示",
        description: "插入数据成功",
        showProgress: true,
        pauseOnHover: true,
        onClose() {
          window.location.reload();
        },
      });
    });

    // 插入数据消息提示
    setIsAddTagModalOpen(false);
  };

  const handleAddTagCancel = () => {
    setIsAddTagModalOpen(false);
  };

  /**
   * Function: 更新选中标签
   */
  const [CurrentUpdateDishTag, setCurrentUpdateDishTag] = useState<DishTagVo>();

  const [UpdateTag] = Form.useForm();

  const [isUpdateTagModalOpen, setIsUpdateTagModalOpen] = useState(false);

  const showUpdateTagModal = (item: DishTagVo) => {
    setCurrentUpdateDishTag(item);

    UpdateTag.setFieldValue("name", item.name);

    UpdateTag.setFieldValue("cate", item.categorizeId);

    setIsUpdateTagModalOpen(true);
  };

  const handleUpdateTagOk = () => {
    updateDishTag({
      id: CurrentUpdateDishTag?.id as number,
      name: UpdateTag.getFieldValue("name"),
      cateId: UpdateTag.getFieldValue("cate"),
    }).then((res) => {
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
    // 插入数据消息提示
    setIsUpdateTagModalOpen(false);
  };

  const handleUpdateTagCancel = () => {
    setIsUpdateTagModalOpen(false);
  };
  /**
   * Function: 删除标签
   */
  const [currentDeleteTag, setCurrentDeleteTag] = useState<number>();

  const [isDeleteTagModalOpen, setIsDeleteTagModalOpen] = useState(false);

  const showDeleteTagModal = (tagId: number) => {
    setCurrentDeleteTag(tagId);
    setIsDeleteTagModalOpen(true);
  };

  const handleDeleteTagOk = () => {
    console.log(currentDeleteTag);

    setTagDataSource((prev) => {
      return prev.filter((element) => {
        if ((element.id as number) !== currentDeleteTag) {
          return element;
        }
      });
    });
    removeDishTag(currentDeleteTag as number).then((res) => {
      console.log(res);
      api.open({
        type: "info",
        message: res.message,
        description: res.data,
        showProgress: true,
        pauseOnHover: true,
      });
    });

    setIsDeleteTagModalOpen(false);
  };

  const handleDeleteTagCancel = () => {
    setIsDeleteTagModalOpen(false);
  };
  /**
   * Function: 批量删除标签
   */
  const [isBatchDeleteModalOpen, setIsBatchDeleteModalOpen] = useState(false);

  const showBatchDeleteModal = () => {
    setIsBatchDeleteModalOpen(true);
  };

  const handleBatchDeleteOk = () => {
    // 页面数据删除
    setTagDataSource((prev) => {
      return prev.filter((item) => {
        if (!selectedRowKeys.includes(item.key)) {
          return item;
        }
      });
    });
    // 消息提示
    if (selectedRowKeys.length) {
      BatchRemoveDishTag(selectedRowKeys).then((res) => {
        api.open({
          type: "success",
          message: res.message,
          description: res.data,
          showProgress: true,
          pauseOnHover: true,
        });
      });
    } else {
      api.open({
        type: "error",
        message: "批量删除失败",
        description: "请选中你需要删除的数据",
        showProgress: true,
        pauseOnHover: true,
      });
    }
    setIsBatchDeleteModalOpen(false);
  };

  const handleBatchDeleteCancel = () => {
    setIsBatchDeleteModalOpen(false);
  };
  /**
   * Function:挂载事件
   */
  useEffect(() => {
    const tagListResponse = tagAllList(); // 所有标签数据

    const cateListResponse = CateList(); // 所有分类数据

    tagListResponse.then((data) => {
      const result = data.data.map((item) => {
        return item ? { ...item, key: item.id } : [];
      });
      setTagDataSource(result as DishTagVo[]);
    });

    cateListResponse.then((data) => {
      const list = data.data.map((item) => {
        return { value: item.id, label: item.name };
      });

      setSearchCate(list);
    });
  }, []);

  return (
    <>
      {contextHolder}
      <Breadcrumb
        items={[
          {
            title: "菜品管理",
          },
          {
            title: "标签管理",
          },
        ]}
      />
      {/**表单 */}
      <div style={{ marginTop: 10 }}>
        <Flex align="center" justify="space-between">
          <Flex align="center">
            <div style={{ marginRight: 5 }}>
              <p style={{ marginBottom: "10px" }}>标签</p>
              <Input
                placeholder="菜品标签搜索"
                value={searchTagName}
                onChange={(e) => {
                  setSearchTagName(e.target.value);
                }}
              />
            </div>
            <div style={{ marginLeft: 5, marginRight: 10 }}>
              <p style={{ marginBottom: "10px" }}>分类</p>
              <Select
                style={{ width: "120px" }}
                onChange={(e) => {
                  setCurrentCateId(e);
                }}
                defaultValue={searchCate[0]}
                options={searchCate}
                placeholder={"分类选择"}
              />
            </div>
            <Button
              type="primary"
              shape="circle"
              onClick={() => {
                console.log(currentCateId, searchTagName);
                searchTagList(searchTagName, currentCateId as number).then(
                  (data) => {
                    const result = data.data.map((item) => {
                      return item ? { ...item, key: item.id } : item;
                    });
                    setTagDataSource(result);
                  }
                );
              }}
              icon={<SearchOutlined />}
            />
          </Flex>
          <Flex>
            <Button
              onClick={() => {
                showAddTagModal();
              }}
              style={{ marginRight: 5 }}
              type="primary"
            >
              新增标签
            </Button>
            <Button
              style={{ marginLeft: 5 }}
              type="primary"
              danger
              onClick={() => {
                if (selectedRowKeys.length) {
                  showBatchDeleteModal();
                } else {
                  api.open({
                    type: "error",
                    message: "消息提示",
                    description: "您还未选中任何数据",
                    showProgress: true,
                    pauseOnHover: true,
                  });
                }
              }}
            >
              批量删除
            </Button>
          </Flex>
        </Flex>
      </div>

      {/**表格标签 */}
      <div style={{ marginTop: 20 }}>
        <Table<DishTagVo>
          rowSelection={{
            selectedRowKeys: selectedRowKeys,
            onChange: TagOnSelectChange,
          }}
          columns={TagFields}
          dataSource={TagDataSource}
          pagination={{
            pageSize: 5,
            showSizeChanger: true, // 是否显示“每页几条”的下拉框
            showQuickJumper: true, // 是否显示“跳转到某页”输入框
            total: TagDataSource.length, // 总数据量（如果后端分页则用实际总数
            current,
            onChange(page, pageSize) {
              localStorage.setItem("tagPage", page);
              setCurrentPage(page);
            },
          }}
        />
      </div>
      {/**新增弹窗 */}
      <Modal
        title="新增标签"
        closable={{ "aria-label": "Custom Close Button" }}
        open={isAddTagModalOpen}
        onOk={handleAddTagOk}
        onCancel={handleAddTagCancel}
      >
        <Form form={InsertTag}>
          <Form.Item
            label="新增标签"
            name="name"
            rules={[{ required: true, message: "Please input your username!" }]}
          >
            <Input />
          </Form.Item>

          <Form.Item
            label="标签分类"
            name="cate"
            rules={[{ required: true, message: "Please input your username!" }]}
          >
            <Select
              onChange={(e) => {
                setCurrentCateId(e);
              }}
              options={searchCate}
            />
          </Form.Item>
        </Form>
      </Modal>
      {/**单个删除 */}
      <Modal
        title="删除标签"
        closable={{ "aria-label": "Custom Close Button" }}
        open={isDeleteTagModalOpen}
        onOk={handleDeleteTagOk}
        onCancel={handleDeleteTagCancel}
      >
        <p>是否删除当前选中数据</p>
      </Modal>

      {/**更新弹窗 */}
      <Modal
        title="更新标签"
        closable={{ "aria-label": "Custom Close Button" }}
        open={isUpdateTagModalOpen}
        onOk={handleUpdateTagOk}
        onCancel={handleUpdateTagCancel}
      >
        <Form form={UpdateTag}>
          <Form.Item
            label="菜品标签"
            name="name"
            rules={[{ required: true, message: "Please input your username!" }]}
          >
            <Input value={CurrentUpdateDishTag?.name} />
          </Form.Item>

          <Form.Item
            label="标签分类"
            name="cate"
            rules={[{ required: true, message: "Please input your username!" }]}
          >
            <Select
              onChange={(e) => {
                setCurrentCateId(e);
              }}
              options={searchCate}
            />
          </Form.Item>
        </Form>
      </Modal>

      {/**批量删除弹窗 */}
      <Modal
        title="批量删除"
        closable={{ "aria-label": "Custom Close Button" }}
        open={isBatchDeleteModalOpen}
        onOk={handleBatchDeleteOk}
        onCancel={handleBatchDeleteCancel}
      >
        <p>是否删除选中的列表?</p>
      </Modal>
    </>
  );
};
