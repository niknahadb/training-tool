'use client';

import { PlusCircleFilled } from '@ant-design/icons';
import { Button, Tag } from 'antd';
import React, { useCallback } from 'react';

import { PRIMARY_COLOR } from '@/components/theme';
import {
  useCreateTeamMutation,
  useFilterTeamsQuery,
  useGetAllCourseCollectionQuery,
  useGetAllProgramsQuery,
  useGetCohortByIdQuery,
  useUpdateTeamMutation,
} from '@/store';
import type { Team } from '@/types/team/team';

import EditableTable from '../components/EditableTable';

const CohortTeamsPage = ({ params }: { params: { cohortId: number } }) => {
  const { data: cohort } = useGetCohortByIdQuery(params.cohortId);
  const { data: programs } = useGetAllProgramsQuery();
  const { data: courseCollections } = useGetAllCourseCollectionQuery();
  const { data: teams, refetch } = useFilterTeamsQuery({
    cohortId: params.cohortId,
  });
  const [updateTeam] = useUpdateTeamMutation();
  const [createTeam] = useCreateTeamMutation();

  const save = async (data: Partial<Team> & { id: number }) => {
    await updateTeam(data);
    await refetch();
  };

  const columns = [
    {
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: 'Program',
      key: 'programId',
      dataIndex: 'program',
      width: 300,
      render: (_: any, { programId }: { programId: number }) => (
        <Tag color="geekblue" key={programId}>
          {programs?.find((program) => program.id === programId)?.name}
        </Tag>
      ),
    },
    {
      title: 'Course Collection',
      key: 'courseCollectionId',
      dataIndex: 'courseCollection',
      width: 300,
      render: (
        _: any,
        { courseCollectionId }: { courseCollectionId: number },
      ) => (
        <Tag color="geekblue" key={courseCollectionId}>
          {
            courseCollections?.find(
              (courseCollection) => courseCollection.id === courseCollectionId,
            )?.name
          }
        </Tag>
      ),
    },
  ];

  const addTeamButtonRender = useCallback(
    () => (
      <Button
        type="text"
        icon={<PlusCircleFilled style={{ color: PRIMARY_COLOR }} />}
        onClick={async () => {
          await createTeam({
            name: 'New Team',
            cohortId: params.cohortId,
            programId: programs?.[0]?.id,
            courseCollectionId: courseCollections?.[0]?.id,
          });
          await refetch();
        }}
      >
        Add Team
      </Button>
    ),
    [],
  );

  if (cohort == null) {
    return <div />;
  }

  return (
    <>
      <EditableTable<Team>
        columns={columns}
        dataSource={teams ?? []}
        footer={addTeamButtonRender}
        updateCallback={save}
      />
    </>
  );
};

export default CohortTeamsPage;
