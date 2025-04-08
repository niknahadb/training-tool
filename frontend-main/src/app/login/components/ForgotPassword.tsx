// eslint-disable-next-line import/no-extraneous-dependencies
import { ArrowLeftOutlined } from '@ant-design/icons';
// eslint-disable-next-line import/no-extraneous-dependencies
import { Button, Flex, Form, Input, message } from 'antd';
import { useRouter } from 'next/navigation';
import React from 'react';

type ForgotPasswordFormValues = {
  email: string;
};

const ForgotPassword: React.FC = () => {
  const [form] = Form.useForm<ForgotPasswordFormValues>();
  const router = useRouter();

  const onFinish = (values: ForgotPasswordFormValues) => {
    // Handle the form submission here
    message.success('A reset code has been sent to your email address.');
  };

  const onBackClick = () => {
    router.back();
  };

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
      <div>
        <Button
          type="link"
          onClick={onBackClick}
          icon={<ArrowLeftOutlined />}
          style={{
            marginBottom: '20px',
            padding: '0',
            display: 'flex',
            alignItems: 'center',
          }}
        >
          Back
        </Button>
      </div>
      <h1 style={{ textAlign: 'left', marginBottom: '10px' }}>
        Forgot Password
      </h1>
      <p style={{ textAlign: 'left', marginBottom: '20px' }}>
        Please enter the email address associated with your account.
      </p>
      <Form
        form={form}
        name="forgotPassword"
        onFinish={onFinish}
        autoComplete="off"
      >
        <Form.Item
          label="Email address"
          name="email"
          rules={[
            { required: true, message: 'Please enter your email address!' },
            { type: 'email', message: 'Please enter a valid email address!' },
          ]}
          style={{ marginBottom: 40 }}
        >
          <Input
            placeholder="Enter Email address"
            style={{ width: '100%', padding: '8px' }}
          />
        </Form.Item>
        <Form.Item style={{ textAlign: 'center', marginBottom: '0' }}>
          <Button
            type="primary"
            htmlType="submit"
            style={{
              width: '100%',
              borderRadius: '20px',
              padding: '10px 0',
              textAlign: 'center',
            }}
          >
            Send Code
          </Button>
        </Form.Item>
      </Form>
    </Flex>
  );
};

export default ForgotPassword;
