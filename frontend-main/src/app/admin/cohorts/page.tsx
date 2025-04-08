'use client';

import { Button, Flex, Typography } from 'antd';
import { redirect } from 'next/navigation';
import React, { useEffect } from 'react';

import { useGetAllCohortsQuery } from '@/store';

const CohortsPage = () => {
  const { data: cohorts } = useGetAllCohortsQuery();

  useEffect(() => {
    if (cohorts?.length != null && cohorts.length > 0) {
      redirect(`/admin/cohorts/${cohorts[0]!.id}/teams`);
    }
  }, [cohorts]);

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
      <Button type="primary">Create Cohort</Button>
    </Flex>
  );
};

export default CohortsPage;
