import { useEffect, useState } from 'react';
// 静态资源
import BackGroundGroundImage1 from '../../assets/login-bg-01.jpg';
import BackGroundGroundImage2 from '../../assets/login-bg-02.jpg';
import Logo from '../../assets/logo.png';
// 静态配置
import { PROJECT_TITLE } from '../../config';
// ANTD组件
import { LockOutlined, UserOutlined, TabletOutlined } from '@ant-design/icons';
import { Button, Form, Input } from 'antd';
// Zustand存储
import { EmailStore } from '../../store/index';
// 自定义组件
import { ReplaceLoginComponentByIndex } from '../../component/login/index';
import { useNavigate } from 'react-router-dom';
// 样式
const LoginCardStyle: React.CSSProperties = {
    color: 'white',
    width: '950px',
    height: '50vh',
    display: 'flex'
}

const LeftLoginCardItemStyle: React.CSSProperties = {
    width: '40%',
    height: '100%',
    backgroundColor: '#F26303',
    borderTopLeftRadius: 15,
    borderBottomLeftRadius: 15,
    opacity: '95%',
    display: 'flex',
    alignItems: 'center'
}

const RightLoginCardItemStyle: React.CSSProperties = {
    width: '60%',
    height: '100%',
    padding: '20px',
    backgroundColor: 'rgba(255,255,255,0.85)',
    borderTopRightRadius: 15,
    borderBottomRightRadius: 15,
    display: 'flex',
    alignItems: 'center'
}

export const Login = () => {

    const navigate = useNavigate();
    // 背景图片索引
    const [ImageIndex, setImageIndex] = useState(0);

    // 背景图片列表
    const [BackGroundList, setBackGroundList] = useState([BackGroundGroundImage1, BackGroundGroundImage2]);

    // 登录标签索引
    const [LoginTagIndex, setLoginTagIndex] = useState(1);//1.密码登录,2:验证码登录;

    // 登录表单实例
    const [LoginForm] = Form.useForm();

    // 登录表单提交
    const onFinish = () => {
        console.log((LoginForm.getFieldsValue()));

        navigate('/');
        // Todo:登录逻辑处理
    };

    // 调用存储
    const { email, setEmail } = EmailStore();

    useEffect(() => {
        const intervalId = setInterval(() => {
            setImageIndex(ImageIndex === BackGroundList.length - 1 ? 0 : ImageIndex + 1);
        }, 5000);
        return (() => { clearInterval(intervalId) });
    }, [ImageIndex, BackGroundList]);

    return (
        <>
            <div
                style={{
                    width: '100%',
                    backgroundImage: `url(${BackGroundList[ImageIndex]})`,
                    backgroundSize: 'cover',
                    backgroundPosition: 'center',
                    backgroundRepeat: 'no-repeat',
                    minHeight: '100vh',
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    transition: 'opacity 1s ease-in-out',
                }}
            >
                <div style={LoginCardStyle}>
                    <div style={LeftLoginCardItemStyle}>
                        <div style={{ width: '100%', textAlign: 'center' }}>
                            <img src={Logo} alt="" style={{ width: '100px' }} />

                            <p style={{ marginTop: '25px', fontSize: '28px', fontWeight: 'bolder' }}>{PROJECT_TITLE}</p>

                            <p style={{ marginTop: '10vh' }}>欢迎登录{PROJECT_TITLE}</p>
                        </div>

                    </div>
                    <div style={RightLoginCardItemStyle}>
                        <div style={{ width: '100%', height: '100%' }}>
                            <div style={{ display: 'flex', textAlign: 'center', lineHeight: '60px', marginBottom: '45px' }}>
                                <p style={{
                                    color: 'black',
                                    width: '50%',
                                    borderBottom: `${LoginTagIndex === 1 ? '5px' : '0px'} orange solid`
                                }} onClick={() => { setLoginTagIndex(1) }}>密码登录</p>
                                <p style={{
                                    color: 'black',
                                    width: '50%',
                                    borderBottom: `${LoginTagIndex === 2 ? '5px' : '0px'} orange solid`
                                }} onClick={() => { setLoginTagIndex(2) }}>验证码登录</p>
                            </div>
                            <Form form={LoginForm} name="login" onFinish={onFinish}>
                                <Form.Item
                                    name="username"
                                    rules={[{ required: true, message: 'Please input your username!' }]}
                                >
                                    <Input
                                        prefix={<UserOutlined />}
                                        placeholder="Username"
                                        style={{
                                            borderRadius: 0,
                                            padding: 15,
                                            borderLeft: '0',
                                            borderRight: '0',
                                            borderTop: '0'
                                        }}
                                        onChange={(e) => {
                                            console.log(e.target.value);

                                            setEmail(e.target.value);
                                        }}
                                    />
                                </Form.Item>
                                {
                                    ReplaceLoginComponentByIndex({ index: LoginTagIndex })
                                }
                                <Form.Item>
                                    <Button block type="primary" onClick={onFinish} style={{ marginTop: '20px', height: '50px', borderRadius: '50px', backgroundColor: '#F26303' }}>
                                        登录
                                    </Button>
                                </Form.Item>
                            </Form>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}