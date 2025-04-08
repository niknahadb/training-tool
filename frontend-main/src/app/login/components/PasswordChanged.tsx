// eslint-disable-next-line import/no-extraneous-dependencies
import { ArrowLeftOutlined } from '@ant-design/icons';
// eslint-disable-next-line import/no-extraneous-dependencies
import { Button, Flex } from 'antd';
import React from 'react';

const PasswordChanged: React.FC = () => {
  const onBackToLoginClick = () => {
    // Handle the back to login button click, e.g., navigate to the login page
    console.log('Back to Login button clicked');
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
      <h1 style={{ marginBottom: '20px' }}>Password changed.</h1>
      <p style={{ marginBottom: '40px' }}>
        Your password has been changed successfully
      </p>
      <Button
        type="primary"
        onClick={onBackToLoginClick}
        icon={<ArrowLeftOutlined />}
        style={{ borderRadius: '20px', padding: '10px 20px' }}
      >
        Back to Login
      </Button>
    </Flex>
  );
};

export default PasswordChanged;
