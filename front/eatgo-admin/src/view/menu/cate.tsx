/* eslint-disable no-unsafe-optional-chaining */
//ANTD组件
import {
  Breadcrumb,
  Table,
  Image,
  Button,
  Modal,
  notification,
  Flex,
  Input,
  Form,
  Upload,
  type UploadProps,
  Badge,
} from "antd";
import {
  ClockCircleOutlined,
  CloseCircleOutlined,
  DeleteOutlined,
  FormOutlined,
  UploadOutlined,
} from "@ant-design/icons";
// Hooks
import { useEffect, useState } from "react";
import { type TableColumnsType, message, type TableProps } from "antd";
// api接口
import {
  CateList,
  removeBanner,
  removeDishCate,
  removeDishCateList,
  searchCateList,
  updateCate,
  upload,
} from "../../api/request/menu";
import type { DishCategorize } from "../../api/entity/menu";

// 分类组件
export const DishCate = () => {
  // 当前分页数据
  const [current, setCurrent] = useState<number>(() => {
    const saved = localStorage.getItem("catePage");
    return saved ? parseInt(saved, 10) || 1 : 1;
  });
  // 消息提示
  const [DeleteCateAlert, DeleteCateContextHolder] =
    notification.useNotification(); //确认提示

  const [DeleteCateListAlert, DeleteCateListContextHolder] =
    notification.useNotification(); //批量删除成功提示

  const [EmptyCateListAlert, EmptyCateListAlertContextHolder] =
    notification.useNotification(); //未选中任何需要删除的数据提示

  // 添加分类表单实例
  const [AddCateform] = Form.useForm();

  // 编辑分类表单实例
  const [EditorCateform] = Form.useForm();

  // 添加分类弹窗状态
  const [AddCateModalOpen, setCateIsModalOpen] = useState(false);

  // 设置表格选中框状态
  const [selectionType, setSelectionType] = useState<"checkbox">("checkbox");

  // 表格数据
  const [CateTableData, setCateTableData] = useState<DishCategorize[]>([]);

  // 批量删除id
  const [DeleteIds, setDeleteIds] = useState<number[]>([]);

  // 当前删除分类
  const [CurrentAboutToDeleteCateItem, setCurrentAboutToDeleteCateItem] =
    useState<DishCategorize | null>(null);

  // 当前编辑分类
  const [CurrentAboutToEditor, setCurrentAboutToEditor] =
    useState<DishCategorize>();

  // 单条删除弹窗状态
  const [isModalOpen, setIsDeleteDishCateModalOpen] = useState(false);

  // 批量删除弹窗状态
  const [isDeleteListOpen, setIsDeleteListModalOpen] = useState(false);

  // 打开弹窗,确定当前需要删除的分类数据-删除分类列表
  const showDeleteListModal = () => {
    setIsDeleteListModalOpen(true);
  };
  const handleDeleteListOk = () => {
    console.log(DeleteIds);
    setCateTableData((prev) => {
      return prev.filter((item) => !DeleteIds.includes(item.id));
    });

    // 批量删除
    removeDishCateList(DeleteIds).then((res) => {
      console.log(res.data);
    });
    DeleteCateListAlert.open({
      message: "批量删除",
      type: "info",
      description: "批量删除成功",
    });
    setIsDeleteListModalOpen(false);
  };
  const handleDeleteListCancel = () => {
    setIsDeleteListModalOpen(false);
  };

  // 打开弹窗,确定当前需要删除的分类数据-删除单个分类
  const showDeleteDishCateModal = (item: DishCategorize) => {
    // 设置当前要删除的菜品分类
    setCurrentAboutToDeleteCateItem(item);
    console.log("删除选中", item);

    setIsDeleteDishCateModalOpen(true);
  };
  // 确认删除按钮点击触发事件
  const DeleteDishCatehandleOk = (item: DishCategorize) => {
    // 确认删除逻辑
    console.log("确认删除", item);

    // 网络请求
    removeDishCate(item).then((res) => {
      // 消息提示
      DeleteCateAlert.open({
        message: "消息提示",
        type: "info",
        description: "菜品删除成功",
        showProgress: true,
        pauseOnHover: true,
      });
    });
    let i = CateTableData.indexOf(item);
    setCateTableData((prev) => {
      prev.splice(i, 1);
      return prev;
    });
    setIsDeleteDishCateModalOpen(false);
  };
  // 取消删除按钮点击触发事件
  const DeleteDishCatehandleCancel = () => {
    setIsDeleteDishCateModalOpen(false);
  };

  //添加分类弹窗
  const showAddCateModal = () => {
    setCateIsModalOpen(true);
  };
  const handleAddCateOk = async () => {
    const values = await AddCateform.validateFields();

    const formData = new FormData();

    formData.append("name", values.name);

    formData.append("icon", values.icon.file);

    values.banner.fileList.forEach((file) => {
      if (file.originFileObj) {
        formData.append("banner", file.originFileObj);
      }
    });

    upload(formData).then((res) => {
      console.log(res.data);

      history.go(0);
    });

    setCateIsModalOpen(false);
  };
  const handleAddCateCancel = () => {
    setCateIsModalOpen(false);
  };

  /**添加分类文件上传 */
  //icon上传逻辑处理
  const [iconFileList, setIconFileList] = useState([]);
  const handleIconChange = ({ fileList }) => {
    const limitedFileList = fileList;
    setIconFileList(limitedFileList);
  };
  //banner上传逻辑处理
  const [BannerFileList, setBannerFileList] = useState([]);
  const handleBannerChange = ({ fileList }) => {
    const limitedFileList = fileList;
    setBannerFileList((prev) => {
      prev = limitedFileList;
      return prev;
    });
  };

  /**编辑分类文件上传 */
  //icon上传逻辑处理
  const [editIconFileList, setEditIconFileList] = useState([]);
  const handleEditIconChange = ({ fileList }) => {
    const limitedFileList = fileList;
    setEditIconFileList(limitedFileList);
  };
  //banner上传逻辑处理
  const [editBannerFileList, setEditBannerFileList] = useState([]);
  const handleEditBannerChange = ({ fileList }) => {
    const limitedFileList = fileList;
    setEditBannerFileList(limitedFileList);
  };
  // 编辑分类弹窗
  const [isEditorModalOpen, setIsEditorModalOpen] = useState(false);
  const showEditorModal = (item: DishCategorize) => {
    console.log("edit", item);
    setCurrentAboutToEditor(item);
    setIsEditorModalOpen(true);
  };
  const handleEditorOk = () => {
    console.log("edited", CurrentAboutToEditor);

    console.log("ed-icon", editIconFileList[0]?.originFileObj);

    const editForm = new FormData();

    console.log("bannerlist", editBannerFileList);

    editForm.append("cate", JSON.stringify(CurrentAboutToEditor));

    editForm.append("icon", editIconFileList[0]?.originFileObj);

    editBannerFileList.forEach((b) => {
      editForm.append("banner", b.originFileObj);
    });
    updateCate(editForm).then((res) => {
      console.log(res.data);

      window.location.reload();
    });

    setIsEditorModalOpen(false);
  };
  const handleEditorCancel = () => {
    setIsEditorModalOpen(false);
  };
  // 表格字段设置
  const columns: TableColumnsType<DishCategorize> = [
    {
      title: "分类名称",
      dataIndex: "name",
    },
    {
      title: "分类Icon",
      dataIndex: "icon",
      render: (text: string) => <img src={text} style={{ width: "100px" }} />,
    },
    {
      title: "分类轮播",
      dataIndex: "banner",
      render: (text: string[]) => (
        <Image.PreviewGroup items={text}>
          <Image width={200} src={text[0]} />
        </Image.PreviewGroup>
      ),
    },
    {
      title: "创建时间",
      dataIndex: "createTime",
      render: (text: string) => (
        <>
          <p>{text.split("T").join(" ")}</p>
        </>
      ),
    },
    {
      title: "最后操作时间",
      dataIndex: "updateTime",
      render: (text: string) => (
        <>
          <p>{text.split("T").join(" ")}</p>
        </>
      ),
    },
    {
      title: "操作",
      width: 200,
      fixed: "left",
      render: (item: DishCategorize) => (
        <>
          <Button
            danger
            icon={<DeleteOutlined />}
            onClick={() => {
              showDeleteDishCateModal(item);
            }}
          />

          <Button
            style={{ marginLeft: 10 }}
            color="purple"
            variant="solid"
            icon={<FormOutlined />}
            onClick={() => {
              showEditorModal(item);
            }}
          />
        </>
      ),
    },
  ];

  // 表格批量选择函数
  const rowSelection: TableProps<DishCategorize>["rowSelection"] = {
    onChange: (
      selectedRowKeys: React.Key[],
      selectedRows: DishCategorize[]
    ) => {
      // console.log(selectedRowKeys, selectedRows);
      let ids: number[] = selectedRows.map((item) => {
        return item.id;
      });
      setDeleteIds(ids);
    },
    getCheckboxProps: (record: DishCategorize) => ({
      disabled: record.name === "Disabled User", // Column configuration not to be checked
      name: record.name,
    }),
  };

  // 获取分类数据同步请求
  const getCateListData = async () => {
    const res = await CateList();
    return res;
  };

  // 挂载数据
  useEffect(() => {
    // 分类列表数据
    getCateListData().then(({ data }) => {
      let list = data.map((item: DishCategorize, index: number) => {
        return {
          ...item,
          banner: JSON.parse(item.banner as unknown as string),
          key: item.id,
        };
      });
      setCateTableData(list);
    });
  }, []);
  return (
    <>
      <div>{DeleteCateContextHolder}</div>

      <div>{DeleteCateListContextHolder}</div>

      <div>{EmptyCateListAlertContextHolder}</div>
      {/**删除当前选中分类 */}
      <Modal
        title="系统提示"
        closable={{ "aria-label": "Custom Close Button" }}
        open={isModalOpen}
        onOk={() => {
          DeleteDishCatehandleOk(
            CurrentAboutToDeleteCateItem as DishCategorize
          );
        }}
        onCancel={DeleteDishCatehandleCancel}
      >
        {CurrentAboutToDeleteCateItem ? (
          <p>
            删除
            <span style={{ color: "#6b21a8" }}>
              {CurrentAboutToDeleteCateItem.name}
            </span>
            后，
            <span style={{ color: "#6b21a8" }}>
              {CurrentAboutToDeleteCateItem.name}
            </span>
            下的所有标签和菜品都将会被删除，继续删除？
          </p>
        ) : (
          <p>加载中...</p>
        )}
      </Modal>
      {/**根据id列表删除分类数据 */}
      <Modal
        title="批量删除提示"
        closable={{ "aria-label": "Custom Close Button" }}
        open={isDeleteListOpen}
        onOk={handleDeleteListOk}
        onCancel={handleDeleteListCancel}
      >
        <p>确定删除你要选中的分类吗?一旦删除将不能恢复</p>
      </Modal>
      {/**添加分类弹窗 */}
      <Modal
        title="消息提示"
        closable={{ "aria-label": "Custom Close Button" }}
        open={AddCateModalOpen}
        onOk={handleAddCateOk}
        okText={"确认"}
        cancelText={"取消"}
        onCancel={handleAddCateCancel}
      >
        <Form form={AddCateform} layout="vertical">
          <Form.Item
            rules={[{ required: true, message: "分类名不得为空" }]}
            name={"name"}
            label="分类名称"
          >
            <Input placeholder="输入菜品分类名称" />
          </Form.Item>
          <Form.Item name={"icon"} label="分类图标">
            <Upload
              listType="picture"
              maxCount={1}
              onChange={handleIconChange}
              beforeUpload={() => false}
              fileList={iconFileList}
              accept="image/*"
            >
              <Button icon={<UploadOutlined />}>上传图标</Button>
            </Upload>
          </Form.Item>
          <Form.Item name={"banner"} label={"分类轮播图"}>
            <Upload
              listType="picture"
              maxCount={5}
              onChange={handleBannerChange}
              beforeUpload={() => false}
              fileList={BannerFileList}
              accept="image/*"
            >
              <Button icon={<UploadOutlined />}>上传图标</Button>
            </Upload>
          </Form.Item>
        </Form>
      </Modal>
      {/**编辑对话框 */}
      <Modal
        title="编辑分类"
        closable={{ "aria-label": "Custom Close Button" }}
        open={isEditorModalOpen}
        onOk={handleEditorOk}
        onCancel={handleEditorCancel}
      >
        <Form
          form={EditorCateform}
          initialValues={{
            name: CurrentAboutToEditor?.name,
            icon: CurrentAboutToEditor?.icon,
            banner: CurrentAboutToEditor?.banner,
          }}
        >
          <Form.Item name={"name"} label="分类名称">
            <Input
              onChange={(e) => {
                setCurrentAboutToEditor((prev) => {
                  return prev ? { ...prev, name: e.target.value } : null;
                });
              }}
            />
          </Form.Item>

          <Form.Item name={"icon"} label="分类图标">
            {CurrentAboutToEditor?.icon ? (
              <Badge
                count={
                  <CloseCircleOutlined
                    style={{ color: "#f5222d" }}
                    onClick={() => {
                      setCurrentAboutToEditor((prev) => {
                        return prev ? { ...prev, icon: "" } : null;
                      });
                    }}
                  />
                }
              >
                <Image width={100} src={CurrentAboutToEditor?.icon} />
              </Badge>
            ) : (
              <Upload
                listType="picture"
                maxCount={1}
                beforeUpload={() => false}
                accept="image/**"
                fileList={editIconFileList}
                onChange={handleEditIconChange}
              >
                <Button icon={<UploadOutlined />}>Click to Upload</Button>
              </Upload>
            )}
          </Form.Item>

          <Form.Item name={"banner"} label="分类轮播">
            <div>
              <Image.PreviewGroup
                preview={{
                  onChange: (current, prev) =>
                    console.log(
                      `current index: ${current}, prev index: ${prev}`
                    ),
                }}
              >
                {CurrentAboutToEditor?.banner.length ? (
                  (CurrentAboutToEditor?.banner as string[]).map(
                    (item, index) => {
                      return (
                        <div key={index} style={{ marginTop: "10px" }}>
                          <Badge
                            onClick={() => {
                              console.log(CurrentAboutToEditor, index);
                              // 服务器数据库数据删除
                              removeBanner(
                                {
                                  ...CurrentAboutToEditor,
                                  banner: JSON.stringify(
                                    CurrentAboutToEditor.banner
                                  ),
                                },
                                index
                              )
                                .then((res) => {
                                  console.log("success回调", res.data);
                                })
                                .catch(() => {
                                  console.log("error");
                                });
                              // 页面数据删除
                              setCurrentAboutToEditor((prev) => {
                                return {
                                  ...prev,
                                  banner: [
                                    // eslint-disable-next-line no-unsafe-optional-chaining
                                    ...prev?.banner.slice(0, index),
                                    ...prev?.banner.slice(index + 1),
                                  ],
                                };
                              });
                            }}
                            count={
                              <CloseCircleOutlined
                                style={{ color: "#f5222d" }}
                              />
                            }
                          >
                            <Image width={200} src={item} />
                          </Badge>
                        </div>
                      );
                    }
                  )
                ) : (
                  <></>
                )}
              </Image.PreviewGroup>
              <Upload
                listType="picture"
                maxCount={6 - editBannerFileList.length}
                beforeUpload={() => false}
                accept="image/**"
                fileList={editBannerFileList}
                onChange={handleEditBannerChange}
              >
                <Button style={{ marginTop: "20px" }} icon={<UploadOutlined />}>
                  Click to Upload
                </Button>
              </Upload>
            </div>
          </Form.Item>
        </Form>
      </Modal>
      {/**面包屑 */}
      <Breadcrumb
        items={[
          {
            title: "菜品管理",
          },
          {
            title: "分类管理",
          },
        ]}
      />
      {/*表格数据*/}
      <Flex justify="space-between" style={{ marginTop: 20 }}>
        <div>
          <Form.Item label="分类名称">
            <Input
              placeholder="输入菜品分类名称"
              onChange={(e) => {
                console.log(e.target.value);

                searchCateList(e.target.value as string).then((data) => {
                  console.log(data.data);

                  if (data.data.length) {
                    let list = data.data.map((item) => {
                      return item
                        ? { ...item, banner: JSON.parse(item.banner) }
                        : item;
                    });
                    setCateTableData(list);
                  } else {
                    setCateTableData([]);
                  }
                });
              }}
            />
          </Form.Item>
        </div>
        <div>
          <Button
            type="primary"
            style={{ marginRight: "10px" }}
            onClick={() => {
              showAddCateModal();
            }}
          >
            新增分类
          </Button>

          <Button
            onClick={() => {
              console.log(DeleteIds);
              if (DeleteIds.length > 0) {
                showDeleteListModal();
              } else {
                EmptyCateListAlert.open({
                  message: "温馨提示",
                  type: "warning",
                  placement: "topLeft",
                  description: "当前还未选选中要删除的分类列表",
                  showProgress: true,
                  pauseOnHover: true,
                });
              }
            }}
            type="primary"
            danger
          >
            批量删除
          </Button>
        </div>
      </Flex>
      <Table<DishCategorize>
        rowSelection={{ type: selectionType, ...rowSelection }}
        columns={columns}
        dataSource={CateTableData}
        pagination={{
          pageSize: 5, // 每页显示多少条
          showSizeChanger: true, // 是否显示“每页几条”的下拉框
          showQuickJumper: true, // 是否显示“跳转到某页”输入框
          total: CateTableData.length, // 总数据量（如果后端分页则用实际总数
          current,
          onChange(page, pageSize) {
            console.log(page, pageSize);
            localStorage.setItem("catePage", page.toString());
            setCurrent(page);
          },
        }}
      />
    </>
  );
};
