import { useState } from 'react';

// ANTD组件
import { LockOutlined, UserOutlined, TabletOutlined } from '@ant-design/icons';
import { Button, Form, Input } from 'antd';

// Zustand存储
import { EmailStore } from '../../store/index';

// 接口
import { sendValidCode } from '../../api/request/user';
import type { Result } from '../../api/entity/base';

// 密码输入框
export const PassWordInputComponent = () => {
    return (
        <>
            <Form.Item
                name="password"
                rules={[{ required: true, message: 'Please input your password!' }]}
            >
                <Input prefix={<LockOutlined />} type="password" placeholder="Password" style={{ borderRadius: 0, padding: 15, borderLeft: '0', borderRight: '0', borderTop: '0' }} />
            </Form.Item>
        </>
    );
}

// 验证码输入框
export const ValidCodeInputComponent = () => {
    // store实例
    const { email } = EmailStore();
    // 倒计时秒数
    const [second, setSecond] = useState(60);
    // 按钮状态管理
    const [ButtonType, setButtonType] = useState(1);//1:开启,0禁用;
    // 倒计时函数
    const CountDown = () => {
        let sec = second;
        let timmer = setInterval(() => {
            sec--;
            if (sec === 0) {
                setButtonType(1);
                clearInterval(timmer);
            }
            console.log(sec);

            setSecond(sec);
        }, 1000)
    };
    return (
        <>
            <Form.Item
                name="validCode"
                rules={[{ required: true, message: 'Please input your validcode!' }]}
            >
                <div style={{ display: 'flex' }}>
                    <Input prefix={<TabletOutlined />} type="password" placeholder="ValidCode" style={{ borderRadius: 0, padding: 15, borderLeft: '0', borderRight: '0', borderTop: '0' }} />
                    {
                        ButtonType === 1 ?
                            <Button
                                color="danger"
                                variant="outlined"
                                style={{
                                    width: '150px',
                                    height: '50px'
                                }}
                                onClick={() => {
                                    CountDown();

                                    setButtonType(0);

                                    // TODO：待封装req1
                                    sendValidCode({ email: email }).then(({ data }) => {
                                        console.log(data as Result<string>);
                                    })
                                }}
                            >
                                点击获取验证码
                            </Button>
                            :
                            <Button color="default" loading disabled={true} variant="outlined" style={{ width: '150px', height: '50px' }}>
                                {second}秒后重新发送
                            </Button>
                    }
                </div>

            </Form.Item>
        </>
    );
}

// 更换登录组件
export const ReplaceLoginComponentByIndex = (index: { index: number }) => {
    const componentMap = new Map<number, React.ComponentType>([
        [1, PassWordInputComponent],
        [2, ValidCodeInputComponent],
    ]);

    const Component = componentMap.get(index.index);

    if (!Component) {
        // 可选：处理无效索引的情况，比如返回 null 或抛出错误
        return null;
    }

    return <Component />;
};