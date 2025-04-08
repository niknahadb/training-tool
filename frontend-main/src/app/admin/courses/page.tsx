'use client';

import { Button, Flex, Typography } from 'antd';
import { redirect } from 'next/navigation';
import React, { useEffect } from 'react';

import { useGetAllCourseQuery } from '@/store';

const CoursesPage = () => {
  const { data: courses } = useGetAllCourseQuery();

  useEffect(() => {
    if (courses?.length != null && courses.length > 0) {
      redirect(`/admin/courses/${courses[0]!.id}`);
    }
  }, [courses]);

  return (
    <Flex
      style={{
        width: '100%',
        justifyContent: 'center',
        alignItems: 'center',
        marginTop: 100,
        flexDirection: 'column',
      }}
    >
      <Typography.Text style={{ marginBottom: 30 }}>
        There are currently no courses.
      </Typography.Text>
      <Button type="primary">Create Course</Button>
    </Flex>
  );
};

export default CoursesPage;
