import {
  Badge,
  Breadcrumb,
  Button,
  Input,
  InputNumber,
  Modal,
  notification,
  Row,
  Select,
  Table,
  Tag,
  Upload,
  type TableColumnsType,
  type TableProps,
  type UploadFile,
} from "antd";
import { Image, Form } from "antd";
import type { DishDto, DishVo } from "../../api/entity/menu";
import { useEffect, useState } from "react";
import {
  BatchDeleteDish,
  CateList,
  deleteDish,
  dishDetailList,
  InsertDish,
  searchDishDetailList,
  searchTagList,
  tagAllList,
  updateDish,
} from "../../api/request/menu";
import {
  CloseOutlined,
  DeleteOutlined,
  PlusOutlined,
  SearchOutlined,
  UploadOutlined,
} from "@ant-design/icons";
import TextArea from "antd/es/input/TextArea";
import { useForm } from "antd/es/form/Form";

export const DishView = () => {
  // 当前页码
  const [current, setCurrentPage] = useState<number>(() => {
    const tagPage = localStorage.getItem("dishPage");

    return tagPage;
  });
  /**
   * Function:消息提示
   */
  const [api, contextHolder] = notification.useNotification();
  /**
   * Function:表格
   */
  const columns: TableColumnsType<DishVo> = [
    {
      title: "菜品标题",
      dataIndex: "title",
      width: 150,
    },
    {
      title: "菜品描述",
      dataIndex: "description",
      width: 220,
    },
    {
      title: "菜品图片",
      dataIndex: "image",
      width: 120,
      render: (path: string) => {
        return (
          <>
            <Image width={100} src={path} />
          </>
        );
      },
    },
    {
      title: "价格",
      dataIndex: "price",
      width: 110,
      render(value: number) {
        return (
          <>
            <p style={{ textAlign: "center" }}>{value}</p>
          </>
        );
      },
    },
    {
      title: "收藏次数",
      dataIndex: "collectionCount",
      width: 110,
      render(value: number) {
        return (
          <>
            <p style={{ textAlign: "center" }}>{value}</p>
          </>
        );
      },
    },
    {
      title: "菜品分类",
      dataIndex: "cateName",
      width: 200,
      render(value: string) {
        return (
          <>
            <Tag color="volcano">{value}</Tag>
          </>
        );
      },
    },
    {
      title: "菜品标签",
      dataIndex: "tagName",
      width: 110,
      render(value: string) {
        return (
          <>
            <Tag color="geekblue">{value}</Tag>
          </>
        );
      },
    },
    {
      title: "创建时间",
      dataIndex: "createTime",
      width: 200,
      render: (time: string) => {
        return <>{time.split("T").join(" ")}</>;
      },
    },
    {
      title: "最后更新时间",
      dataIndex: "updateTime",
      width: 200,
      render: (time: string) => {
        return <>{time.split("T").join(" ")}</>;
      },
    },
    {
      title: "操作",
      width: 152,
      fixed: "right",
      render: (item: DishVo) => {
        return (
          <>
            <Row>
              <Button
                color="danger"
                variant="text"
                onClick={() => {
                  showDeleteModal(item);
                }}
              >
                删除
              </Button>
              <Button
                color="purple"
                variant="text"
                onClick={() => {
                  setCurrentUpdateDish(item);

                  showUpdateDishModal();
                }}
              >
                编辑
              </Button>
            </Row>
          </>
        );
      },
    },
  ];

  const [tableData, setTableData] = useState<DishVo[]>();

  const rowSelection: TableProps<DishVo>["rowSelection"] = {
    onChange: (selectedRowKeys: React.Key[], selectedRows: DishVo[]) => {
      setCurrentBatchDeleteDish(selectedRows);
    },
  };

  /**
   * Function:搜索
   */
  const [searchForm] = Form.useForm();

  const [searchCateSelect, setSerchCateSelect] =
    useState<{ value: number; label: string }[]>();

  const [searchTagSelect, setSearchTagSelect] =
    useState<{ value: number; label: string }[]>();

  const handleSerchButtonClick = () => {
    const title = searchForm.getFieldValue("title");
    const dishCateId = searchForm.getFieldValue("categorizeId");
    const dishTagId = searchForm.getFieldValue("tagId");

    searchDishDetailList({ title, dishCateId, dishTagId }).then((res) => {
      console.log(res.data);
      const result = res.data.map((item) => {
        return { ...item, key: item.id };
      });
      setTableData(result);
    });
  };

  /**
   * Function:新增菜品
   */
  const [addDishForm] = Form.useForm();

  const [isAddDishModalOpen, setIsAddDishModalOpen] = useState(false);

  const [dishImgList, setDishImgList] = useState<UploadFile[]>([]);

  const handleDishImageChange = ({ fileList }) => {
    const limitedFileList = fileList;
    setDishImgList(limitedFileList);
  };

  const showAddDishModal = () => {
    setIsAddDishModalOpen(true);
  };

  const handleAddDishOk = () => {
    const title = addDishForm.getFieldValue("title");
    const categorizeId = addDishForm.getFieldValue("categorizeId");
    const tagId = addDishForm.getFieldValue("tagId");
    const description = addDishForm.getFieldValue("description");
    const price = addDishForm.getFieldValue("price");
    console.log(dishImgList[0].originFileObj);

    const dishData: DishDto = {
      title,
      categorizeId,
      tagId,
      description,
      price,
    };

    const form = new FormData();

    form.append("dishImg", dishImgList[0].originFileObj);

    form.append("dishData", JSON.stringify(dishData));

    InsertDish(form).then((res) => {
      console.log(res.data);
      api.open({
        type: "success",
        message: "消息提示",
        description: "数据新增成功",
        showProgress: true,
        pauseOnHover: true,
        onClose() {
          window.location.reload();
        },
      });
    });

    setIsAddDishModalOpen(false);
  };

  const handleAddDishCancel = () => {
    setIsAddDishModalOpen(false);
  };
  /**
   * Function:删除菜品
   */
  const [currentDeleteDish, setCurrentDeleteDish] = useState<DishVo>();

  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);

  const showDeleteModal = (item: DishVo) => {
    setCurrentDeleteDish(item);

    setIsDeleteModalOpen(true);
  };

  const handleDeleteOk = () => {
    console.log("删除菜品", currentDeleteDish);

    deleteDish(currentDeleteDish as DishVo).then((res) => {
      console.log(res);
      api.open({
        message: "消息提示",
        type: "success",
        description: res.message,
        showProgress: true,
        pauseOnHover: true,
        onClose() {
          window.location.reload();
        },
      });
    });

    setIsDeleteModalOpen(false);
  };

  const handleDeleteCancel = () => {
    setIsDeleteModalOpen(false);
  };

  /**
   * Function:批量删除
   */
  const [currentBatchDeleteDish, setCurrentBatchDeleteDish] =
    useState<DishVo[]>();

  const [isBatchDeleteModalOpen, setIsBatchDeleteModalOpen] = useState(false);

  const showBatchDeleteModal = () => {
    console.log(currentBatchDeleteDish);

    setIsBatchDeleteModalOpen(true);
  };

  const handleBatchDeleteOk = () => {
    console.log("删除菜品", currentBatchDeleteDish);

    if (currentBatchDeleteDish?.length) {
      // 删除页面数据
      setTableData((prev) => {
        return prev?.filter((item) => {
          if (!currentBatchDeleteDish?.includes(item)) {
            return item;
          }
        });
      });

      // 删除服务器数据
      BatchDeleteDish(currentBatchDeleteDish as DishVo[]).then((res) => {
        console.log(res);
        api.open({
          type: "success",
          message: "消息提示",
          description: "删除数据成功",
          showProgress: true,
          pauseOnHover: true,
        });
      });
    } else {
      api.open({
        type: "error",
        message: "消息警告",
        description: "您还未选中任何数据啊",
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
   * Function:编辑数据
   */

  const [updateForm] = useForm();

  const [currentUpdateDish, setCurrentUpdateDish] = useState<DishVo>();

  const [isUpdateDishModalOpen, setIsUpdateDishModalOpen] = useState(false);

  const showUpdateDishModal = () => {
    setIsUpdateDishModalOpen(true);
  };

  const handleUpdateDishOk = () => {
    console.log(currentUpdateDish);

    const form = new FormData();

    if (dishImgList.length) {
      form.append("image", dishImgList[0].originFileObj);
    }

    form.append("dish", JSON.stringify(currentUpdateDish));

    updateDish(form).then((res) => {
      api.open({
        type: "success",
        message: "消息提示",
        description: "数据更新成功",
        showProgress: true,
        pauseOnHover: true,
        onClose() {
          window.location.reload();
        },
      });
    });

    setIsUpdateDishModalOpen(false);
  };

  const handleUpdateDishCancel = () => {
    setIsUpdateDishModalOpen(false);
  };
  /**
   * Function:挂载函数
   */
  useEffect(() => {
    CateList().then((res) => {
      console.log("分类", res.data);
      const result = res.data.map((item) => {
        return { label: item.name, value: item.id };
      });

      setSerchCateSelect(result);
    });

    tagAllList().then((res) => {
      console.log("标签", res.data);
      const result = res.data.map((item) => {
        return { label: item.name, value: item.id };
      });

      setSearchTagSelect(result);
    });

    dishDetailList().then((res) => {
      console.log("菜品", res.data);

      const dishList = res.data.map((item) => {
        return { ...item, key: item.id };
      });
      setTableData(dishList);
    });
  }, []);

  return (
    <>
      {/**添加菜品弹窗 */}
      {contextHolder}
      {/**新增弹窗 */}
      <div>
        <Modal
          title="新增菜品"
          closable={{ "aria-label": "Custom Close Button" }}
          open={isAddDishModalOpen}
          onOk={handleAddDishOk}
          onCancel={handleAddDishCancel}
        >
          <Form form={addDishForm}>
            <Form.Item required label="菜品名称" name={"title"}>
              <Input placeholder="菜品标题" />
            </Form.Item>

            <Form.Item required label="菜品分类" name={"categorizeId"}>
              <Select
                placeholder="菜品分类"
                onChange={(e) => {
                  console.log(e);
                  searchTagList("", e).then((res) => {
                    console.log(res.data);
                    const result = res.data.map((item) => {
                      return { label: item.name, value: item.id };
                    });

                    setSearchTagSelect(result);
                  });
                }}
                options={searchCateSelect}
              />
            </Form.Item>

            <Form.Item required label="菜品标签" name={"tagId"}>
              <Select placeholder="菜品标签" options={searchTagSelect} />
            </Form.Item>

            <Form.Item required label="菜品价格" name={"price"}>
              <InputNumber<string>
                style={{ width: 200 }}
                min="0"
                max="100000"
                step="1.0"
                stringMode
              />
            </Form.Item>

            <Form.Item required label="菜品图片">
              <Upload
                accept="image/*"
                listType="picture-card"
                maxCount={1}
                fileList={dishImgList}
                beforeUpload={() => {
                  return false;
                }}
                onChange={handleDishImageChange}
              >
                <UploadOutlined />
              </Upload>
            </Form.Item>

            <Form.Item required label="菜品描述" name="description">
              <TextArea
                showCount
                maxLength={100}
                onChange={() => {}}
                placeholder="输入100字以内的描述"
                style={{ height: 150, resize: "none" }}
              />
            </Form.Item>
          </Form>
        </Modal>
      </div>
      {/**删除弹窗 */}
      <div>
        <Modal
          title="菜品数据"
          closable={{ "aria-label": "Custom Close Button" }}
          open={isDeleteModalOpen}
          onOk={handleDeleteOk}
          onCancel={handleDeleteCancel}
        >
          <p>确定删除当前菜品?</p>
        </Modal>
      </div>
      {/**批量删除弹窗 */}
      <div>
        <Modal
          title="批量删除"
          closable={{ "aria-label": "Custom Close Button" }}
          open={isBatchDeleteModalOpen}
          onOk={handleBatchDeleteOk}
          onCancel={handleBatchDeleteCancel}
        >
          <p>确定要删除选中的菜品?</p>
        </Modal>
      </div>
      {/**编辑弹窗 */}
      <div>
        <Modal
          title="编辑菜品"
          closable={{ "aria-label": "Custom Close Button" }}
          open={isUpdateDishModalOpen}
          onOk={handleUpdateDishOk}
          onCancel={handleUpdateDishCancel}
        >
          <Form form={updateForm}>
            <Form.Item required label="菜品名称">
              <Input
                onChange={(e) => {
                  setCurrentUpdateDish((prev) => {
                    return { ...prev, title: e.target.value };
                  });
                }}
                value={currentUpdateDish?.title}
                placeholder="菜品标题"
              />
            </Form.Item>

            <Form.Item required label="菜品分类">
              <Select
                value={currentUpdateDish?.categorizeId}
                placeholder="菜品分类"
                onChange={(e) => {
                  // console.log(e);
                  setCurrentUpdateDish((prev) => {
                    return { ...prev, categorizeId: e };
                  });
                  searchTagList("", e).then((res) => {
                    console.log(res.data);
                    const result = res.data.map((item) => {
                      return { label: item.name, value: item.id };
                    });

                    setSearchTagSelect(result);
                  });
                }}
                options={searchCateSelect}
              />
            </Form.Item>

            <Form.Item required label="菜品标签">
              <Select
                onChange={(e) => {
                  setCurrentUpdateDish((prev) => {
                    return { ...prev, tagId: e };
                  });
                }}
                value={currentUpdateDish?.tagId}
                placeholder="菜品标签"
                options={searchTagSelect}
              />
            </Form.Item>

            <Form.Item required label="菜品价格">
              <InputNumber
                style={{ width: 200 }}
                value={currentUpdateDish?.price}
                onChange={(e) => {
                  setCurrentUpdateDish((prev) => {
                    return { ...prev, price: e };
                  });
                }}
                min={0}
                max={100000}
                step={1.0}
                stringMode
              />
            </Form.Item>

            <Form.Item required label="菜品图片">
              <Row>
                {currentUpdateDish?.image ? (
                  <Badge
                    count={
                      <CloseOutlined
                        style={{ width: "10px", color: "#f5222d" }}
                      />
                    }
                    onClick={() => {
                      setCurrentUpdateDish((prev) => {
                        return { ...prev, image: "" };
                      });
                    }}
                  >
                    <Image width={100} src={currentUpdateDish?.image} />
                  </Badge>
                ) : (
                  <>
                    <Upload
                      accept="image/*"
                      listType="picture-card"
                      maxCount={1}
                      fileList={dishImgList}
                      beforeUpload={() => {
                        return false;
                      }}
                      onChange={handleDishImageChange}
                      style={{
                        marginLeft: currentUpdateDish?.image.length
                          ? "20px"
                          : "0px",
                      }}
                    >
                      <UploadOutlined />
                    </Upload>
                  </>
                )}
              </Row>
            </Form.Item>

            <Form.Item required label="菜品描述">
              <TextArea
                showCount
                value={currentUpdateDish?.description}
                maxLength={100}
                onChange={(e) => {
                  setCurrentUpdateDish((prev) => {
                    return { ...prev, description: e.target.value };
                  });
                }}
                placeholder="输入100字以内的描述"
                style={{ height: 150, resize: "none" }}
              />
            </Form.Item>
          </Form>
        </Modal>
      </div>
      {/**面包屑 */}
      <div>
        <Breadcrumb
          items={[
            {
              title: "菜品管理",
            },
            {
              title: "菜品列表",
            },
          ]}
        />
      </div>
      {/**表单 */}
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          marginTop: "15px",
          alignItems: "center",
        }}
      >
        <Form
          form={searchForm}
          layout="inline"
          style={{ display: "flex", alignItems: "center" }}
        >
          <Form.Item layout="vertical" label="菜品" name={"title"}>
            <Input placeholder="菜品标题" />
          </Form.Item>

          <Form.Item
            style={{ marginLeft: "10px" }}
            layout="vertical"
            label="菜品分类"
            name={"categorizeId"}
          >
            <Select
              placeholder="菜品分类"
              onChange={(e) => {
                console.log(e);
                searchTagList("", e).then((res) => {
                  console.log(res.data);
                  const result = res.data.map((item) => {
                    return { label: item.name, value: item.id };
                  });

                  setSearchTagSelect(result);
                });
              }}
              options={searchCateSelect}
            />
          </Form.Item>

          <Form.Item
            style={{ marginLeft: "10px" }}
            layout="vertical"
            label="菜品标签"
            name={"tagId"}
          >
            <Select placeholder="菜品标签" options={searchTagSelect} />
          </Form.Item>

          <Form.Item style={{ marginLeft: "10px" }}>
            <Button
              color="purple"
              variant="solid"
              icon={<SearchOutlined />}
              onClick={handleSerchButtonClick}
              style={{ padding: "20px" }}
            >
              点击搜索
            </Button>
          </Form.Item>
        </Form>

        <div>
          <Button
            style={{ padding: 20 }}
            icon={<DeleteOutlined />}
            color="danger"
            variant="solid"
            onClick={() => {
              console.log(currentBatchDeleteDish);

              if (currentBatchDeleteDish === undefined) {
                api.open({
                  type: "error",
                  message: "消息警告",
                  description: "您还未选中任何数据啊",
                  showProgress: true,
                  pauseOnHover: true,
                });
              } else {
                showBatchDeleteModal();
              }
            }}
          >
            批量删除
          </Button>
          <Button
            style={{ padding: 20, marginLeft: "10px" }}
            icon={<PlusOutlined />}
            color="primary"
            variant="solid"
            onClick={() => {
              showAddDishModal();
            }}
          >
            新增菜品
          </Button>
        </div>
      </div>
      {/**表格 */}
      <div style={{ marginTop: 15 }}>
        <Table<DishVo>
          rowSelection={rowSelection}
          columns={columns}
          dataSource={tableData}
          scroll={{ x: 1200 }}
          pagination={{
            total: tableData?.length,
            defaultPageSize: 5,
            current,
            onChange(page: number) {
              console.log(page);
              setCurrentPage(page);
              localStorage.setItem("dishPage", page);
            },
          }}
        />
      </div>
    </>
  );
};
