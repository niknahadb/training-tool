'use client';

import { Button, Flex, Typography } from 'antd';
import { redirect } from 'next/navigation';
import React, { useEffect } from 'react';

import { useGetAllCourseCollectionQuery } from '@/store';

const CourseCollectionsPage = () => {
  const { data: collections } = useGetAllCourseCollectionQuery();

  useEffect(() => {
    if (collections?.length != null && collections.length > 0) {
      redirect(`/admin/course-collections/${collections[0]!.id}`);
    }
  }, [collections]);

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
        There are currently no collections.
      </Typography.Text>
      <Button type="primary">Create Course Collection</Button>
    </Flex>
  );
};

export default CourseCollectionsPage;
