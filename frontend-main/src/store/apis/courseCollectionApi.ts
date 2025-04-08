import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

import type { CourseCollection } from '@/types/courseCollection/courseCollection';

export const courseCollectionApi = createApi({
  reducerPath: 'course-collections',
  baseQuery: fetchBaseQuery({
    baseUrl: 'http://localhost:8080',
  }),
  endpoints: (builder) => ({
    createCourseCollection: builder.mutation<
      CourseCollection,
      Partial<CourseCollection>
    >({
      query: (courseCollection) => ({
        url: '/course-collections/create',
        method: 'POST',
        body: courseCollection,
      }),
    }),
    getCourseCollectionById: builder.query<CourseCollection, number>({
      query: (id) => ({
        url: `/course-collections/${id}`,
        method: 'GET',
      }),
    }),
    getAllCourseCollection: builder.query<CourseCollection[], void>({
      query: () => ({
        url: '/course-collections',
        method: 'GET',
      }),
    }),
    deleteCourseCollectionById: builder.mutation<CourseCollection, number>({
      query: (id) => ({
        url: `/course-collections/${id}/delete`,
        method: 'DELETE',
      }),
    }),
    updateCourseCollectionById: builder.mutation<
      CourseCollection,
      Partial<CourseCollection>
    >({
      query: (courseCollection) => ({
        url: `/course-collections/${courseCollection.id}/update`,
        method: 'PUT',
        body: courseCollection,
      }),
    }),
    addCourseToCourseCollection: builder.mutation<
      CourseCollection,
      { courseCollectionId: number; teamId: number }
    >({
      query: ({ courseCollectionId, teamId }) => ({
        url: `/course-collections/${courseCollectionId}/add-course/${teamId}`,
        method: 'PUT',
      }),
    }),
  }),
});

export const {
  useCreateCourseCollectionMutation,
  useGetCourseCollectionByIdQuery,
  useGetAllCourseCollectionQuery,
  useDeleteCourseCollectionByIdMutation,
  useUpdateCourseCollectionByIdMutation,
  useAddCourseToCourseCollectionMutation,
} = courseCollectionApi;
