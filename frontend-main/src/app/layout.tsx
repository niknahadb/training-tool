'use client';

// import '../styles/global.css';

import { ConfigProvider } from 'antd';
import enUS from 'antd/locale/en_US';
import { type PropsWithChildren, useEffect, useState } from 'react';
import { Provider, useDispatch } from 'react-redux';

import { PRIMARY_COLOR } from '@/components/theme';
import { setCredentials } from '@/store/slices/authSlice';
import type { AuthState } from '@/types/user/user';

import { store } from '../store';

const Wrapper = ({ children }: PropsWithChildren) => {
  const dispatch = useDispatch();
  const [fetchedOnce, setFetechedOnce] = useState(false);

  useEffect(() => {
    const cachedAuthState: AuthState = JSON.parse(
      localStorage.getItem('user_info') ?? '{}',
    );

    // console.log('cachedAuthState', cachedAuthState);

    dispatch(setCredentials(cachedAuthState));
    setFetechedOnce(true);
  }, []);

  if (!fetchedOnce) {
    return <div />;
  }

  return <>{children}</>;
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body>
        <Provider store={store}>
          <ConfigProvider
            locale={enUS}
            theme={{
              token: {
                colorPrimary: PRIMARY_COLOR,
              },
              components: {
                Button: {
                  primaryShadow: 'none',
                },
              },
            }}
          >
            <Wrapper>{children}</Wrapper>
          </ConfigProvider>
        </Provider>
      </body>
    </html>
  );
}
