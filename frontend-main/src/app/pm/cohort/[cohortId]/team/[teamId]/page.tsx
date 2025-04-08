'use client';

// import '@/styles/fonts.css';
// import '@/styles/global.css';

import { Button, Typography } from 'antd';
// import en_US from 'antd/lib/locale-provider/en_US';
import React, { useState } from 'react';

import AppLayout from '@/components/AppLayout/AppLayout';
import TeamProgressTile from '@/components/TeamProgressTile';
import { PRIMARY_COLOR } from '@/components/theme';
import UserTile from '@/components/UserTile';
import { useFilterUsersQuery } from '@/store';
import type { User } from '@/types/user/user';

const ProjectManagerPage = ({
  params,
}: {
  params: { teamId: number; cohortId: number };
}) => {
  const [pmSelected, setPmSelected] = useState(false); // Add this line
  const [designersSelected, setDesignersSelected] = useState(false);
  const [developersSelected, setDevelopersSelected] = useState(false);

  const designQuery: Partial<User> = {
    role: 'DESIGN',
    teamId: params.teamId,
  };
  const softwareQuery: Partial<User> = {
    role: 'SOFTWARE',
    teamId: params.teamId,
  };
  const pmQuery: Partial<User> = {
    role: 'PM',
    teamId: params.teamId,
  };

  const { data: developers } = useFilterUsersQuery(softwareQuery);
  const { data: designers } = useFilterUsersQuery(designQuery);
  const { data: pms } = useFilterUsersQuery(pmQuery);

  // const getUserCourses = (userId: number): String[] => {
  //   const { data: courseProgress } = useGetAllCourseProgressQuery();
  //   const userCourseIds: number[] = [];

  //   if (courseProgress) {
  //     courseProgress.forEach((progress: CourseProgress) => {
  //       if (progress.userId === userId) {
  //         userCourseIds.push(progress.courseId);
  //       }
  //     });
  //   }
  //   const userCourseNames: String[] = [];
  //   userCourseIds.forEach((courseId) => {
  //     const { data: courses } = useFetchCourseQuery({ id: courseId });
  //     if (courses[0]) {
  //       userCourses.push(courses[0].name);
  //     }
  //   });
  //   return userCourseNames;
  // };

  return (
    <AppLayout>
      <Typography.Title style={{ color: PRIMARY_COLOR, marginBottom: 20 }}>
        Welcome Caden!
      </Typography.Title>
      <Typography.Title style={{ marginBottom: 25 }}>My Team</Typography.Title>
      <TeamProgressTile teamId={1} />
      <Typography.Title style={{ marginTop: 60 }} level={2}>
        Project Manager
        <Button
          type="link"
          onClick={() => {
            setPmSelected(!pmSelected);
          }}
        >
          {pmSelected ? 'Deselect All' : 'Select All'}
        </Button>
      </Typography.Title>
      {(pms ?? [])?.map((pm: User) => (
        <UserTile
          key={pm.id}
          name={`${pm.firstName} ${pm.lastName}`}
          userRole="#ProjectManager"
          pfp="https://api.dicebear.com/7.x/miniavs/svg?seed=3"
          selected={pmSelected}
          ID={pm.id}
        />
      ))}

      <Typography.Title style={{ marginTop: 30 }} level={2}>
        Developers
        <Button
          type="link"
          onClick={() => {
            setDevelopersSelected(!developersSelected);
          }}
        >
          {developersSelected ? 'Deselect All' : 'Select All'}
        </Button>
      </Typography.Title>

      {(developers ?? [])?.map((developer: User) => (
        <UserTile
          key={developer.id}
          name={`${developer.firstName} ${developer.lastName}`}
          userRole="#Developer"
          pfp="https://api.dicebear.com/7.x/miniavs/svg?seed=2"
          selected={developersSelected}
          ID={developer.id}
          // courseNames={getUserCourses(developer.id)}
        />
      ))}

      <Typography.Title style={{ marginTop: 30 }} level={2}>
        Designers
        <Button
          type="link"
          onClick={() => {
            setDesignersSelected(!designersSelected);
          }}
        >
          {designersSelected ? 'Deselect All' : 'Select All'}
        </Button>
      </Typography.Title>

      {(designers ?? [])?.map((designer: User) => (
        <UserTile
          key={designer.id}
          name={`${designer.firstName} ${designer.lastName}`}
          userRole="#Designer"
          pfp="https://api.dicebear.com/7.x/miniavs/svg?seed=3"
          selected={designersSelected}
          ID={designer.id}
          // courseNames={getUserCourses(designer.id)}
        />
      ))}
      <div style={{ marginBottom: '50px' }} />
    </AppLayout>
  );
};

export default ProjectManagerPage;
