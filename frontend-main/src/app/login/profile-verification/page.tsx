'use client';

import { Flex } from 'antd';
import Image from 'next/image';
import * as React from 'react';
// eslint-disable-next-line import/no-extraneous-dependencies
import { Helmet, HelmetProvider } from 'react-helmet-async';

import ProfileVerification from '../components/ProfileVerification';

const ForgotPasswordPage = () => {
  return (
    <HelmetProvider>
      <Helmet>
        <title>Profile Verification - CodeLab</title>
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
          <ProfileVerification />
        </Flex>
      </div>
    </HelmetProvider>
  );
};

export default ForgotPasswordPage;
