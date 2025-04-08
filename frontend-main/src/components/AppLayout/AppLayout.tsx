'use client';

import type { MenuDataItem } from '@ant-design/pro-layout';
import type { SiderMenuProps } from '@ant-design/pro-layout/es/components/SiderMenu/SiderMenu';
import type { Route } from '@ant-design/pro-layout/es/typing';
import { Flex } from 'antd';
import dynamic from 'next/dynamic';
import Image from 'next/image';
import Link from 'next/link';
import { usePathname } from 'next/navigation';
import type { PropsWithChildren, ReactNode } from 'react';
import React, { useCallback } from 'react';

import { BACKGROUND_COLOR } from '../theme';

const ProLayout = dynamic(() => import('@ant-design/pro-layout'), {
  ssr: false,
});

interface AppLayoutProps extends PropsWithChildren {
  children?: ReactNode;
  route?: Route;
  footer?: ReactNode;
  modal?: ReactNode;
}

const AppLayout = ({ children, route, footer, modal }: AppLayoutProps) => {
  const pathname = usePathname();

  const titleRender = useCallback(
    (logo: ReactNode) => (
      <Link href="/" style={{ marginInline: 20 }}>
        {logo}
      </Link>
    ),
    [],
  );

  const Logo = (
    <Image
      src="/images/white-logo.svg"
      height={35}
      width={(88 / 35) * 35}
      alt="CodeLab Logo"
    />
  );

  const menuItemRender = useCallback(
    (item: MenuDataItem, dom: ReactNode) => (
      <Link href={item.path ?? '/'}>{dom}</Link>
    ),
    [],
  );

  const menuFooterRender = (props: SiderMenuProps | undefined) => {
    if (props?.collapsed) {
      return undefined;
    }
    return <Flex style={{ paddingInline: 8, paddingTop: 8 }}>{footer}</Flex>;
  };

  return (
    <ProLayout
      token={{
        header: {
          colorBgHeader: 'black',
        },
        sider: {
          colorMenuBackground: '#393d3d',
        },
        bgLayout: BACKGROUND_COLOR,
      }}
      logo={Logo}
      prefixCls="my-prefix"
      location={{
        pathname,
      }}
      // siderMenuType="group"
      headerTitleRender={titleRender}
      menuItemRender={menuItemRender}
      menuFooterRender={menuFooterRender}
      fixSiderbar
      layout="mix"
      splitMenus
      collapsedButtonRender={false}
      route={route}
      contentWidth="Fixed"
      siderWidth={270}
    >
      {children}
      {modal}
    </ProLayout>
  );
};

export default AppLayout;
