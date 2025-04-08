'use client';

import { EllipsisOutlined } from '@ant-design/icons';
import type { MenuProps } from 'antd';
import { Button, Divider, Dropdown, Flex, Typography } from 'antd';
import Image from 'next/image';
import { useState } from 'react';

import { HBox } from '@/components/custom/HBox/HBox';
import { PRIMARY_COLOR } from '@/components/theme';
import {
  useDeleteCourseByIdMutation,
  useGetAllCourseQuery,
  useGetCourseByIdQuery,
} from '@/store';

const { Title, Text } = Typography;

const CoursesPage = ({ params }: { params: { courseId: number } }) => {
  const { data: course } = useGetCourseByIdQuery(params.courseId);
  const { refetch } = useGetAllCourseQuery();
  const [deleteCourse] = useDeleteCourseByIdMutation();
  const [isModalOpen, setIsModalOpen] = useState(false);

  const showModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    refetch();
  };

  const onClick: MenuProps['onClick'] = async ({ key }) => {
    if (key === 'edit') {
      showModal();
    } else if (key === 'delete') {
      await deleteCourse(params.courseId);
      await refetch();
    }
  };

  const moreActionsMenuItems: MenuProps['items'] = [
    {
      key: 'edit',
      label: 'Edit Course Header',
    },
    {
      key: 'delete',
      danger: true,
      label: 'Delete Course',
    },
  ];

  if (course == null) {
    return <div />;
  }

  return (
    <>
      <Flex
        style={{
          backgroundColor: 'white',
          borderRadius: 5,
          boxShadow: '0 0 16px rgb(0 0 0 / 0.2)',
          padding: 50,
          flexDirection: 'column',
        }}
      >
        <HBox>
          <Flex style={{ flexDirection: 'column' }}>
            <div style={{ display: 'flex' }}>
              <Title
                style={{ color: PRIMARY_COLOR, marginRight: 20, marginTop: 10 }}
              >
                {course.name}
              </Title>
              <Dropdown
                menu={{ items: moreActionsMenuItems, onClick }}
                trigger={['click']}
              >
                <Button
                  type="primary"
                  shape="circle"
                  icon={<EllipsisOutlined />}
                  style={{ marginTop: 22 }}
                  size="small"
                />
              </Dropdown>
            </div>
            <Text>{course?.description}</Text>
            <Button
              type="primary"
              style={{ marginTop: 50, width: 150 }}
              size="large"
            >
              Edit Course
            </Button>
          </Flex>
          <div
            style={{
              border: '2px solid #64D9F7',
              borderRadius: '50%',
              aspectRatio: '1 / 1',
              marginRight: 50,
            }}
          >
            <Image
              src="https://t3.ftcdn.net/jpg/02/51/30/52/360_F_251305284_M7NOdeDXcXx44WkUWkHQijztn3yneroq.jpg"
              alt="Course Image"
              width={200}
              height={200}
              style={{
                borderRadius: '100%',
              }}
            />
          </div>
        </HBox>
        <Divider style={{ borderWidth: 3 }} />
        <Title level={2}>Modules</Title>
        <Flex style={{ flexDirection: 'column', marginTop: 30 }}>
          {course.modules.map((module, index) => (
            <Flex key={module.id} style={{ flexDirection: 'column' }}>
              <HBox spacing="large" align="start">
                <Flex
                  style={{
                    border: '2px solid black',
                    width: 40,
                    height: 40,
                    borderRadius: '50%',
                    alignItems: 'center',
                    justifyContent: 'center',
                  }}
                >
                  <Title style={{ margin: 0 }} level={3}>
                    {index + 1}
                  </Title>
                </Flex>
                <Flex style={{ flexDirection: 'column' }}>
                  <Title level={3} style={{ margin: 0 }}>
                    {module.name}
                  </Title>
                  <Text>{module.description}</Text>
                </Flex>
              </HBox>
            </Flex>
          ))}
        </Flex>
      </Flex>
    </>
  );
};

export default CoursesPage;
