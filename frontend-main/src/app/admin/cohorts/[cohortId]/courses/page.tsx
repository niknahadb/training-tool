'use client';

import React from 'react';

const CohortCoursesPage = ({ params }: { params: { cohortId: number } }) => {
  return <div>Cohort courses Page {params.cohortId ?? 'no cohort'}</div>;
};

export default CohortCoursesPage;
