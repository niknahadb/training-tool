'use client';

import { Flex } from 'antd';
import Image from 'next/image';
import * as React from 'react';
// eslint-disable-next-line import/no-extraneous-dependencies
import { Helmet, HelmetProvider } from 'react-helmet-async';

import CreateAccountForm from '../components/CreateAccount';

const CreateAccountPage = () => {
  return (
    <HelmetProvider>
      <Helmet>
        <title>Create Account - CodeLab</title>
        <meta name="description" content="Login - CodeLab" />
      </Helmet>
      <div
        style={{
          height: '100vh',
          backgroundImage: `url(/images/background2.png)`,
        }}
      >
        <Flex align="center" vertical style={{ paddingTop: 70 }}>
          <Image
            src="/images/white-logo.svg"
            height={100}
            width={(88 / 35) * 100}
            alt="CodeLab Logo"
            style={{ marginBottom: 30 }}
          />
          <CreateAccountForm />
        </Flex>
      </div>
    </HelmetProvider>
  );
};

export default CreateAccountPage;
