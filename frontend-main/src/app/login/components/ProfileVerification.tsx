// eslint-disable-next-line import/no-extraneous-dependencies
import { ArrowLeftOutlined } from '@ant-design/icons';
// eslint-disable-next-line import/no-extraneous-dependencies
import { Button, Flex, Form, Input, message } from 'antd';
import React from 'react';

type VerificationFormValues = {
  code: string;
};

const ProfileVerification: React.FC = () => {
  const [form] = Form.useForm<VerificationFormValues>();

  const onFinish = () => {
    // Handle the form submission here
    message.success('Code verified successfully.');
  };

  const onBackClick = () => {
    // Handle the back button click, e.g., navigate to the previous page
    console.log('Back button clicked');
  };

  const onResendClick = () => {
    // Handle the resend code logic here
    message.info('Verification code resent.');
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
        Please check your email.
      </h1>
      <p style={{ textAlign: 'left', marginBottom: '20px' }}>
        Enter the 4-digit code that you received on your email.
      </p>
      <Form
        form={form}
        name="profileVerification"
        onFinish={onFinish}
        autoComplete="off"
      >
        <Form.Item
          name="code"
          rules={[
            { required: true, message: 'Please enter the verification code!' },
          ]}
          style={{ marginBottom: '16px' }}
        >
          <Input
            placeholder="Enter 4-digit code"
            maxLength={4}
            style={{ width: '100%', padding: '8px', textAlign: 'center' }}
          />
        </Form.Item>
        <Form.Item style={{ textAlign: 'center', marginBottom: '16px' }}>
          <Button
            type="primary"
            htmlType="submit"
            style={{ width: '100%', borderRadius: '20px', padding: '10px 0' }}
          >
            Verify
          </Button>
        </Form.Item>
      </Form>
      <div style={{ textAlign: 'center' }}>
        <p style={{ margin: '10px 0' }}>Didn’t receive a code?</p>
        <Button
          type="link"
          onClick={onResendClick}
          style={{ padding: '0', margin: '0' }}
        >
          Resend
        </Button>
      </div>
    </Flex>
  );
};

export default ProfileVerification;
