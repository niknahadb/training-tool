import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

import type { CourseProgress } from '@/types/courseProgress/courseProgress';

export const courseProgressApi = createApi({
  reducerPath: 'courseProgress',
  baseQuery: fetchBaseQuery({
    baseUrl: 'http://localhost:8080',
  }),
  endpoints: (builder) => ({
    createCourseProgress: builder.mutation<
      CourseProgress,
      Partial<CourseProgress>
    >({
      query: (courseProgress) => ({
        url: '/course-progress/create',
        method: 'POST',
        body: courseProgress,
      }),
    }),
    getCourseProgressById: builder.query<CourseProgress, number>({
      query: (id) => ({
        url: `/course-progress/${id}`,
        method: 'GET',
      }),
    }),
    updateCourseProgressById: builder.mutation<
      CourseProgress,
      { id: number; updatedProgress: CourseProgress }
    >({
      query: ({ id, updatedProgress }) => ({
        url: `/course-progress/${id}/update`,
        method: 'PUT',
        body: updatedProgress,
      }),
    }),
    deleteCourseProgressById: builder.mutation<void, number>({
      query: (id) => ({
        url: `/course-progress/${id}/delete`,
        method: 'DELETE',
      }),
    }),
    getAllCourseProgress: builder.query<CourseProgress[], void>({
      query: () => ({
        url: `/course-progress`,
        method: 'GET',
      }),
    }),
    getCompletedCourseProgresses: builder.query<CourseProgress[], number>({
      query: (teamId) => ({
        url: `/course-progress/team/${teamId}/completed`,
        method: 'GET',
      }),
    }),
    getInProgressCourseProgresses: builder.query<CourseProgress[], number>({
      query: (teamId) => ({
        url: `/course-progress/team/${teamId}/in-progress`,
        method: 'GET',
      }),
    }),
    getNotStartedCourseProgresses: builder.query<CourseProgress[], number>({
      query: (teamId) => ({
        url: `/course-progress/team/${teamId}/not-started`,
        method: 'GET',
      }),
    }),

    getPercentageCompleted: builder.query<number, number>({
      query: (id) => ({
        url: `/${id}/course-percent`,
        method: 'GET',
      }),
    }),
  }),
});

export const {
  useCreateCourseProgressMutation,
  useGetCourseProgressByIdQuery,
  useUpdateCourseProgressByIdMutation,
  useDeleteCourseProgressByIdMutation,

  useGetAllCourseProgressQuery,
  useGetCompletedCourseProgressesQuery,
  useGetInProgressCourseProgressesQuery,
  useGetNotStartedCourseProgressesQuery,
  useGetPercentageCompletedQuery,
} = courseProgressApi;
