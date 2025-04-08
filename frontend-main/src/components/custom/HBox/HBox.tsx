import { Flex, Space } from 'antd';
import type { SizeType } from 'antd/es/config-provider/SizeContext';
import type { PropsWithChildren } from 'react';
import React from 'react';

export interface HBoxProps {
  spacing?: SizeType;
  style?: React.CSSProperties;
  align?: 'center' | 'start' | 'end';
}

export const HBox = ({
  children,
  spacing,
  style,
  align = 'center',
}: PropsWithChildren<HBoxProps>) => {
  return (
    <>
      {spacing ? (
        <Space style={style} align={align} size={spacing}>
          {children}
        </Space>
      ) : (
        <Flex
          vertical={false}
          justify="space-between"
          style={style}
          align={align}
        >
          {children}
        </Flex>
      )}
    </>
  );
};
