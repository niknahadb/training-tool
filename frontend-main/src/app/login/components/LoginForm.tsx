// eslint-disable-next-line import/no-extraneous-dependencies
import type { FormProps } from 'antd';
import { Button, Checkbox, Flex, Form, Input, Typography } from 'antd';
import { useRouter } from 'next/navigation';
import React from 'react';

import { HBox } from '@/components/custom/HBox/HBox';

const { Title } = Typography;

type FieldType = {
  title: string;
  email: string;
  password: string;
  remember?: string;
};

const onFinish: FormProps<FieldType>['onFinish'] = (values) => {
  console.log('Success:', values);
};

const onFinishFailed: FormProps<FieldType>['onFinishFailed'] = (errorInfo) => {
  console.log('Failed:', errorInfo);
};

const App: React.FC = () => {
  const router = useRouter();

  return (
    <Flex
      style={{
        flexDirection: 'column',
        backgroundColor: 'white',
        padding: 30,
        borderRadius: 8,
        width: 500,
      }}
    >
      <Form
        name="basic"
        labelCol={{ span: 24 }} // Labels take up full width
        wrapperCol={{ span: 24 }} // Wrappers take up full width
        initialValues={{ remember: true }}
        onFinish={onFinish}
        onFinishFailed={onFinishFailed}
        autoComplete="off"
      >
        <Form.Item<FieldType>
          label="Email"
          name="email"
          rules={[{ required: true, message: 'Please input your email!' }]}
          style={{ marginBottom: '8px' }}
        >
          <Input />
        </Form.Item>

        <Form.Item<FieldType>
          label="Password"
          name="password"
          rules={[{ required: true, message: 'Please input your password!' }]}
          style={{ maxWidth: 1000, margin: '0 auto', marginBottom: '8px' }}
        >
          <Input.Password />
        </Form.Item>

        <HBox style={{ marginTop: 15, marginBottom: 30 }}>
          <Form.Item name="remember" valuePropName="checked" noStyle>
            <Checkbox>Remember Me</Checkbox>
          </Form.Item>

          <a href="/login/forgot-password" style={{ textAlign: 'right' }}>
            Forgot password
          </a>
        </HBox>

        <Form.Item wrapperCol={{ span: 24 }} style={{ textAlign: 'center' }}>
          <Button
            type="primary"
            htmlType="submit"
            style={{ borderRadius: '8px', width: '100%', marginBottom: '15px' }}
          >
            Login
          </Button>
          <Button
            htmlType="submit"
            style={{ borderRadius: '8px', width: '100%' }}
            onClick={() => router.push('/login/create-account')}
          >
            Create Account
          </Button>
        </Form.Item>
      </Form>
    </Flex>
  );
};

export default App;
