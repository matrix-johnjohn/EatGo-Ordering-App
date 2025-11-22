import { Breadcrumb, Button, Input } from "antd";
import { useEffect, useState } from "react";
import { userList } from "../../api/request/user/index";
import type { User } from "../../api/entity/user/index";
import type { ChatQuery, Message } from "../../api/entity/chat";
import { chatHistory } from "../../api/request/chat";

export const Chat: React.FC = () => {
  const [ws, setWs] = useState<WebSocket | null>(null);
  // 发送信息
  const [textValue, setTextValue] = useState<string>();
  //用户数据列表
  const [users, setUserList] = useState<User[]>();

  //聊天记录
  const [chats, setChatHistory] = useState<Message[]>();

  // 当前聊天用户
  const [currentUser, setCurrentUser] = useState<User>(() => {
    const data = localStorage.getItem("chat-user") as string;
    return JSON.parse(data) as User;
  });
  // 挂载函数
  useEffect(() => {
    if (currentUser) {
      chatHistory({ to: currentUser.id, from: 1 } as ChatQuery).then((res) => {
        setChatHistory(res.data.data);
      });
    }

    userList().then(({ data }) => {
      const eles = data.data;

      setUserList(eles);
    });
  }, []);

  useEffect(() => {
    const socket = new WebSocket(`ws://localhost:8080/ws?userId=${1}`);

    socket.onopen = () => {
      console.log("✅ Connected");
      setWs(socket);
    };

    socket.onmessage = (event) => {
      const msg = event.data;
      if (msg === "欢迎连接 WebSocket 服务器！") {
        console.log("连接成功");
      } else {
        console.log("收到消息:", msg);
        setChatHistory((prev: Message[]) => {
          const obj = JSON.parse(msg);
          const mess = obj as Message;
          return [...prev, mess];
        });
      }
    };

    socket.onclose = () => {
      console.log("❌ Connection closed");
      setWs(null);
    };

    socket.onerror = (error) => {
      console.error("WebSocket error:", error);
    };

    return () => {
      socket.close();
    };
  }, []);
  //发送信息
  return (
    <>
      <Breadcrumb
        items={[
          {
            title: "用户管理",
          },
          {
            title: "用户聊天",
          },
        ]}
      />
      {/**用户数据 */}
      <div style={{ display: "flex", justifyContent: "space-evenly" }}>
        <div
          style={{
            width: "35%",
            marginTop: "20px",
            backgroundColor: "white",
            borderRadius: "10px",
          }}
        >
          {users?.map((item) => {
            if (item.id !== 1) {
              return (
                <div
                  key={item.id}
                  style={{
                    width: "100%",
                    display: "flex",
                    margin: "10px",
                    borderRadius: "10px",
                  }}
                  onClick={() => {
                    setCurrentUser(item);
                    localStorage.setItem("chat-user", JSON.stringify(item));
                    chatHistory({ from: 1, to: item.id }).then((res) => {
                      console.log(res.data.data);
                      setChatHistory(res.data.data);
                    });
                  }}
                >
                  <img
                    style={{
                      width: "70px",
                      marginRight: "10px",
                      borderRadius: "10px",
                    }}
                    src={item.avatar}
                    alt=""
                  />
                  <div>
                    <p
                      style={{
                        fontSize: "17px",
                        fontWeight: "bold",
                        margin: "5px",
                      }}
                    >
                      {item.username}
                    </p>
                    <p style={{ color: "gray", marginTop: "10px" }}>
                      {"测试数据聊天"}
                    </p>
                  </div>
                </div>
              );
            }
          })}
        </div>
        <div
          style={{
            width: "60%",
            height: "80vh",
            overflow: "scroll",
            backgroundColor: "white",
            padding: "10px",
            position: "relative",
          }}
        >
          {chats?.map((item, index) => {
            return (
              <div key={index} style={{ width: "100%" }}>
                <div
                  style={{
                    display: "flex",
                    justifyContent: item.from === 1 ? "flex-end" : "flex-start",
                  }}
                >
                  <p
                    style={{
                      borderRadius: "20px",
                      width: "50%",
                      backgroundColor: item.from === 1 ? "orange" : "pink",
                      marginTop: "10px",
                      padding: "20px",
                      textAlign: item.from === 1 ? "right" : "left",
                    }}
                  >
                    {item.message}
                  </p>
                </div>
              </div>
            );
          })}

          <div style={{ display: "flex", position: "absolute", bottom: "0px" }}>
            <Input
              placeholder="发送信息"
              onChange={(e) => {
                setTextValue(e.target.value);
              }}
            />
            <Button
              style={{ marginLeft: "15px" }}
              color="danger"
              variant="solid"
              onClick={() => {
                if (!ws || ws.readyState !== WebSocket.OPEN) {
                  console.warn("❌ WebSocket 未连接，无法发送消息");
                  return;
                }

                const msg = {
                  from: 1,
                  to: currentUser.id,
                  message: textValue,
                };
                setChatHistory((prev) => {
                  return [...prev, msg];
                });
                ws.send(JSON.stringify(msg));
                console.log("发送消息:", msg);

                setTextValue("");
              }}
            >
              发送
            </Button>
          </div>
        </div>
      </div>
    </>
  );
};
