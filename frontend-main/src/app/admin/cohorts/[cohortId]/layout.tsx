'use client';

import { EllipsisOutlined } from '@ant-design/icons';
import type { MenuProps } from 'antd';
import { Button, Dropdown, Typography } from 'antd';
import { usePathname } from 'next/navigation';
import type { PropsWithChildren } from 'react';
import { useEffect, useState } from 'react';

import CreateCohortModal from '@/components/CreateCohortModal/CreateCohortModal';
import { PRIMARY_COLOR } from '@/components/theme';
import { useDeleteCohortByIdMutation, useGetCohortByIdQuery } from '@/store';

const { Title } = Typography;

const AdminCohortLayout = ({
  children,
  params,
}: PropsWithChildren & { params: { cohortId: number } }) => {
  const { data: cohort, refetch } = useGetCohortByIdQuery(params.cohortId);
  const [deleteCohort] = useDeleteCohortByIdMutation();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const pathname = usePathname();
  const path = pathname.split('/');
  const [pageType, setPageType] = useState('');

  useEffect(() => {
    if (path[path.length - 1] === 'teams') {
      setPageType('Teams');
    } else if (path[path.length - 1] === 'associates') {
      setPageType('Associates');
    }
  }, [pathname]);

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
      await deleteCohort(params.cohortId);
    }
  };

  const moreActionsMenuItems: MenuProps['items'] = [
    {
      key: 'edit',
      label: 'Edit Cohort',
    },
    {
      key: 'delete',
      danger: true,
      label: 'Delete Cohort',
    },
  ];

  if (cohort == null) {
    return <div />;
  }

  return (
    <>
      <div style={{ display: 'flex' }}>
        <Title level={2} style={{ color: PRIMARY_COLOR, marginRight: 20 }}>
          {cohort?.type === 'FALL' ? 'FQ' : 'WSQ'}{' '}
          {cohort?.year.toString().substring(2)} {pageType}
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
      <CreateCohortModal
        isModalOpen={isModalOpen}
        closeModal={closeModal}
        type="update"
        initialValues={cohort}
      />
      {children}
    </>
  );
};

export default AdminCohortLayout;
