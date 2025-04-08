'use client';

import React from 'react';

import { useGetCurrentCohortQuery } from '@/store';
import { useGetAllProgramsQuery } from '@/store/apis/programApi';

import ProgramCard from './components/ProgramCard';

const Admin = () => {
  // const user = {
  //   firstName: 'Abhi',
  //   lastName: 'W',
  //   email: 'test@test.com',
  //   role: 'SOFTWARE',
  //   cohortId: 1,
  //   teamId: 1,
  //   programId: 1,
  // };

  // const { data: cohorts } = useGetAllCohortsQuery();
  const { data: selectedCohort } = useGetCurrentCohortQuery();
  const { data: programs } = useGetAllProgramsQuery();

  console.log('selectedCohort', selectedCohort);
  console.log('programs', programs);

  if (!selectedCohort) {
    return <div />;
  }

  return (
    <>
      {programs?.map((program) => (
        <ProgramCard
          key={program.id}
          programId={program.id}
          cohortId={selectedCohort.id}
        />
      ))}
    </>
  );
};

export default Admin;
