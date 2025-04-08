// eslint-disable-next-line import/no-extraneous-dependencies
import { ArrowLeftOutlined } from '@ant-design/icons';
// eslint-disable-next-line import/no-extraneous-dependencies
import { Button, Checkbox, Col, Flex, Form, Input, message, Row } from 'antd';
import { useRouter } from 'next/navigation';
import React from 'react';

type CreateAccountFormValues = {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  confirmPassword: string;
  agreeToTerms: boolean;
};

const CreateAccount: React.FC = () => {
  const [form] = Form.useForm<CreateAccountFormValues>();
  const router = useRouter();

  const onFinish = (values: CreateAccountFormValues) => {
    // Handle the form submission here
    if (values.password !== values.confirmPassword) {
      message.error('Passwords do not match!');
    } else {
      message.success('Account created successfully.');
    }
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
        Create Account
      </h1>
      <Form
        form={form}
        name="createAccount"
        onFinish={onFinish}
        autoComplete="off"
        layout="vertical"
      >
        <Row gutter={16}>
          <Col span={12}>
            <Form.Item
              label="First name"
              name="firstName"
              rules={[
                { required: true, message: 'Please enter your first name!' },
              ]}
            >
              <Input placeholder="ex. Joe" />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item
              label="Last name"
              name="lastName"
              rules={[
                { required: true, message: 'Please enter your last name!' },
              ]}
            >
              <Input placeholder="ex. Smith" />
            </Form.Item>
          </Col>
        </Row>
        <Form.Item
          label="Email address"
          name="email"
          rules={[
            { required: true, message: 'Please enter your email address!' },
            { type: 'email', message: 'Please enter a valid email address!' },
          ]}
        >
          <Input placeholder="example@gmail.com" />
        </Form.Item>
        <Form.Item
          label="Create Password"
          name="password"
          rules={[
            { required: true, message: 'Please enter your password!' },
            { min: 8, message: 'Password must be at least 8 characters!' },
          ]}
        >
          <Input.Password placeholder="Must be 8 characters" />
        </Form.Item>
        <Form.Item
          label="Confirm Password"
          name="confirmPassword"
          rules={[{ required: true, message: 'Please confirm your password!' }]}
        >
          <Input.Password placeholder="Repeat password" />
        </Form.Item>
        <Form.Item
          name="agreeToTerms"
          valuePropName="checked"
          rules={[
            {
              required: true,
              message: 'You must agree to the terms and conditions!',
            },
          ]}
        >
          <Checkbox>I agree to terms and conditions</Checkbox>
        </Form.Item>
        <Form.Item style={{ textAlign: 'center', marginBottom: '0' }}>
          <Button
            type="primary"
            htmlType="submit"
            style={{ width: '100%', borderRadius: '20px', padding: '10px 0' }}
          >
            Create Account
          </Button>
        </Form.Item>
      </Form>
      <div style={{ textAlign: 'center', marginTop: '20px' }}>
        <p style={{ margin: '10px 0' }}>Already have an account?</p>
        <Button
          type="link"
          onClick={() => console.log('Login clicked')}
          style={{ padding: '0', margin: '0' }}
        >
          Login
        </Button>
      </div>
    </Flex>
  );
};

export default CreateAccount;
