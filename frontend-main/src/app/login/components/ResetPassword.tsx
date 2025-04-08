// eslint-disable-next-line import/no-extraneous-dependencies
import { ArrowLeftOutlined } from '@ant-design/icons';
// eslint-disable-next-line import/no-extraneous-dependencies
import { Button, Flex, Form, Input, message } from 'antd';
import React from 'react';

type ResetPasswordFormValues = {
  newPassword: string;
  confirmPassword: string;
};

const ResetPassword: React.FC = () => {
  const [form] = Form.useForm<ResetPasswordFormValues>();

  const onFinish = (values: ResetPasswordFormValues) => {
    // Handle the form submission here
    if (values.newPassword !== values.confirmPassword) {
      message.error('Passwords do not match!');
    } else {
      message.success('Password updated successfully.');
    }
  };

  const onBackClick = () => {
    // Handle the back button click, e.g., navigate to the previous page
    console.log('Back button clicked');
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
        Reset Password
      </h1>
      <p style={{ textAlign: 'left', marginBottom: '20px' }}>
        Please type something you’ll remember.
      </p>
      <Form
        form={form}
        name="resetPassword"
        onFinish={onFinish}
        autoComplete="off"
      >
        <Form.Item
          label="New Password"
          name="newPassword"
          rules={[
            { required: true, message: 'Please enter your new password!' },
            { min: 8, message: 'Password must be at least 8 characters!' },
          ]}
          style={{ marginBottom: '16px' }}
        >
          <Input.Password
            placeholder="Must be 8 characters"
            style={{ width: '100%', padding: '8px' }}
          />
        </Form.Item>
        <Form.Item
          label="Confirm Password"
          name="confirmPassword"
          rules={[{ required: true, message: 'Please confirm your password!' }]}
          style={{ marginBottom: 40 }}
        >
          <Input.Password
            placeholder="Repeat password"
            style={{ width: '100%', padding: '8px' }}
          />
        </Form.Item>
        <Form.Item style={{ textAlign: 'center', marginBottom: '0' }}>
          <Button
            type="primary"
            htmlType="submit"
            style={{ width: '100%', borderRadius: '20px', padding: '10px 0' }}
          >
            Update Password
          </Button>
        </Form.Item>
      </Form>
    </Flex>
  );
};

export default ResetPassword;
