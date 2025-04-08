'use client';

import { Tag } from 'antd';
import React from 'react';

import {
  useFilterUsersQuery,
  useGetAllProgramsQuery,
  useGetAllTeamsQuery,
  useGetCohortByIdQuery,
  useUpdateUserMutation,
} from '@/store';
import type { User } from '@/types/user/user';

import EditableTable from '../components/EditableTable';

const CohortAssociatesPage = ({ params }: { params: { cohortId: number } }) => {
  const { data: cohort } = useGetCohortByIdQuery(params.cohortId);
  const { data: programs } = useGetAllProgramsQuery();
  const { data: teams } = useGetAllTeamsQuery();
  const { data: users, refetch } = useFilterUsersQuery({
    cohortId: params.cohortId,
  });
  const [updateUser] = useUpdateUserMutation();

  const save = async (data: Partial<User> & { id: number }) => {
    await updateUser(data);
    await refetch();
  };

  const columns = [
    {
      title: 'First Name',
      dataIndex: 'firstName',
      key: 'firstName',
    },
    {
      title: 'Last Name',
      dataIndex: 'lastName',
      key: 'lastName',
    },
    {
      title: 'Team',
      dataIndex: 'teamId',
      key: 'team',
      render: (_: any, { teamId }: { teamId: number }) => (
        <Tag color="geekblue" key={teamId}>
          {teams?.find((team) => team.id === teamId)?.name}
        </Tag>
      ),
    },
    {
      title: 'Program',
      dataIndex: 'programId',
      key: 'program',
      render: (_: any, { programId }: { programId: number }) => (
        <Tag color="geekblue" key={programId}>
          {programs?.find((program) => program.id === programId)?.name}
        </Tag>
      ),
    },
  ];

  if (cohort == null) {
    return <div />;
  }

  return (
    <>
      <EditableTable<User>
        columns={columns}
        dataSource={users ?? []}
        updateCallback={save}
      />
    </>
  );
};

export default CohortAssociatesPage;
