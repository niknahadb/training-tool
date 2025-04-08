'use client';

import { PlusCircleFilled } from '@ant-design/icons';
import type { MenuProps } from 'antd';
import { Button, Dropdown, Tag } from 'antd';
import { useCallback, useEffect, useState } from 'react';

import { PRIMARY_COLOR } from '@/components/theme';
import {
  useAddCourseToCourseCollectionMutation,
  useGetAllCourseQuery,
  useGetCourseCollectionByIdQuery,
  useUpdateCourseByIdMutation,
  useUpdateCourseCollectionByIdMutation,
} from '@/store';
import type { Course } from '@/types/course/course';
import type { CourseCollection } from '@/types/courseCollection/courseCollection';

import EditableTable from '../../cohorts/[cohortId]/components/EditableTable';

const CohortCollectionPage = ({
  params,
}: {
  params: { courseCollectionId: number };
}) => {
  const { data: courseCollection, refetch } = useGetCourseCollectionByIdQuery(
    params.courseCollectionId,
  );
  const { data: courses } = useGetAllCourseQuery();
  const [updateCourse] = useUpdateCourseByIdMutation();
  const [updateCourseCollection] = useUpdateCourseCollectionByIdMutation();
  const [addCourse] = useAddCourseToCourseCollectionMutation();
  const [collectionCourses, setCollectionCourses] = useState<Course[]>([]);

  useEffect(() => {
    setCollectionCourses(courseCollection?.courses ?? []);
  }, [courseCollection]);

  const save = async (data: Partial<CourseCollection> & { id: number }) => {
    await updateCourseCollection(data);
    await refetch();
  };

  const columns = [
    {
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: 'Type',
      key: 'type',
      dataIndex: 'type',
      width: 300,
      render: (_: any, { type }: { type: String }) => (
        <Tag color="geekblue">{type}</Tag>
      ),
    },
  ];

  const onClick: MenuProps['onClick'] = async ({ key }) => {
    await addCourse({
      teamId: parseInt(key, 10),
      courseCollectionId: params.courseCollectionId,
    });
    await refetch();
  };

  const moreActionsMenuItems: MenuProps['items'] = courses?.map((course) => ({
    key: course.id,
    label: course.name,
  }));

  const addCollectionButtonRender = useCallback(
    () => (
      <Dropdown
        menu={{ items: moreActionsMenuItems, onClick }}
        trigger={['click']}
      >
        <Button
          type="text"
          icon={<PlusCircleFilled style={{ color: PRIMARY_COLOR }} />}
        >
          Add Course
        </Button>
      </Dropdown>
    ),
    [],
  );

  if (courseCollection == null) {
    return <div />;
  }

  return (
    <>
      <EditableTable<Course>
        columns={columns}
        dataSource={collectionCourses ?? []}
        footer={addCollectionButtonRender}
        updateCallback={save}
      />
    </>
  );
};

export default CohortCollectionPage;
