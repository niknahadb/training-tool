'use client';

import { EllipsisOutlined } from '@ant-design/icons';
import type { MenuProps } from 'antd';
import { Button, Dropdown, Typography } from 'antd';
import type { PropsWithChildren } from 'react';
import { useState } from 'react';

import CreateCollectionModal from '@/components/CreateCollectionModal.tsx/CreateCollectionModal';
import { PRIMARY_COLOR } from '@/components/theme';
import {
  useDeleteCourseCollectionByIdMutation,
  useGetAllCourseCollectionQuery,
  useGetCourseCollectionByIdQuery,
} from '@/store';

const { Title } = Typography;

const AdminCourseCollectionLayout = ({
  children,
  params,
}: PropsWithChildren & { params: { courseCollectionId: number } }) => {
  const { data: courseCollection } = useGetCourseCollectionByIdQuery(
    params.courseCollectionId,
  );
  const { refetch } = useGetAllCourseCollectionQuery();
  const [deleteCourseCollection] = useDeleteCourseCollectionByIdMutation();
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
      await deleteCourseCollection(params.courseCollectionId);
      await refetch();
    }
  };

  const moreActionsMenuItems: MenuProps['items'] = [
    {
      key: 'edit',
      label: 'Edit Collection',
    },
    {
      key: 'delete',
      danger: true,
      label: 'Delete Collection',
    },
  ];

  if (courseCollection == null) {
    return <div />;
  }

  return (
    <>
      <div style={{ display: 'flex' }}>
        <Title level={2} style={{ color: PRIMARY_COLOR, marginRight: 20 }}>
          {courseCollection.name} Collection
        </Title>
        <Dropdown
          menu={{ items: moreActionsMenuItems, onClick }}
          trigger={['click']}
        >
          <Button
            type="primary"
            shape="circle"
            icon={<EllipsisOutlined />}
            style={{ marginTop: 32 }}
            size="small"
          />
        </Dropdown>
      </div>
      <CreateCollectionModal
        isModalOpen={isModalOpen}
        closeModal={closeModal}
        type="update"
        initialValues={courseCollection}
      />
      {children}
    </>
  );
};

export default AdminCourseCollectionLayout;
