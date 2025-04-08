'use client';

import { Button } from 'antd';
import { usePathname } from 'next/navigation';
import type { PropsWithChildren } from 'react';
import { useEffect, useState } from 'react';

import AppLayout from '@/components/AppLayout/AppLayout';
import CreateCohortModal from '@/components/CreateCohortModal/CreateCohortModal';
import CreateCollectionModal from '@/components/CreateCollectionModal.tsx/CreateCollectionModal';
import {
  useGetAllCohortsQuery,
  useGetAllCourseCollectionQuery,
  useGetAllCourseQuery,
} from '@/store';
import type { Cohort } from '@/types/cohort/cohort';

const AdminLayout = ({ children }: PropsWithChildren) => {
  const { data: cohorts } = useGetAllCohortsQuery();
  const { data: courseCollections } = useGetAllCourseCollectionQuery();
  const { data: courses } = useGetAllCourseQuery();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const pathname = usePathname();
  const path = pathname.split('/');
  const [pageType, setPageType] = useState<'Cohort' | 'Collection' | 'Course'>(
    'Cohort',
  );

  const getFooterText = (
    pathInput: string[],
  ): 'Cohort' | 'Collection' | 'Course' => {
    if (pathInput.length > 2 && pathInput[2] === 'course-collections') {
      return 'Collection';
    }
    if (pathInput.length > 2 && pathInput[2] === 'courses') {
      return 'Course';
    }
    return 'Cohort';
  };

  useEffect(() => {
    setPageType(getFooterText(path));
  }, [pathname]);

  const showModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const cohortRoutes = cohorts?.map((cohort: Cohort) => ({
    path: `/admin/cohorts/${cohort.type.toLowerCase()}${cohort.year}`,
    name: `${cohort.type === 'FALL' ? 'Fall' : 'Winter/Spring'} ${cohort.year}`,
    routes: [
      {
        path: `/admin/cohorts/${cohort.id}/teams`,
        name: 'Teams',
      },
      {
        path: `/admin/cohorts/${cohort.id}/associates`,
        name: 'Associates',
      },
    ],
  }));

  const courseCollectionRoutes = courseCollections?.map((courseCollection) => ({
    path: `/admin/course-collections/${courseCollection.id}`,
    name: courseCollection.name,
  }));

  const courseRoutes = courses?.map((course) => ({
    path: `/admin/courses/${course.id}`,
    name: course.name,
    routes: [
      {
        path: `/admin/courses/${course.id}/overview`,
        name: `Overview`,
      },
      ...course.modules.map((module) => ({
        path: `/admin/courses/${course.id}/module/${module.id}`,
        name: module.name,
        routes: module.lessons.map((lesson) => ({
          path: `/admin/courses/${course.id}/module/${module.id}/lesson/${lesson.id}`,
          name: lesson.name,
        })),
      })),
    ],
  }));

  return (
    <AppLayout
      route={{
        path: '/admin',
        routes: [
          {
            path: '/admin/courses',
            name: 'Courses',
            routes: courseRoutes ?? [],
          },
          {
            path: '/admin/course-collections',
            name: 'Collections',
            routes: courseCollectionRoutes ?? [],
          },
          {
            path: '/admin/cohorts',
            name: 'Cohorts',
            routes: cohortRoutes ?? [],
          },
        ],
      }}
      modal={
        <>
          {pageType === 'Cohort' ? (
            <CreateCohortModal
              isModalOpen={isModalOpen}
              closeModal={closeModal}
            />
          ) : (
            <CreateCollectionModal
              isModalOpen={isModalOpen}
              closeModal={closeModal}
            />
          )}
        </>
      }
      footer={
        <Button block type="primary" onClick={showModal}>
          Create {pageType}
        </Button>
      }
    >
      {children}
    </AppLayout>
  );
};

export default AdminLayout;
