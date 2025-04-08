import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

import type { Course } from '@/types/course/course';

export const courseApi = createApi({
  reducerPath: 'course',
  baseQuery: fetchBaseQuery({
    baseUrl: 'http://localhost:8080',
  }),
  endpoints: (builder) => ({
    createCourse: builder.mutation<Course, Partial<Course>>({
      query: (course) => ({
        url: '/courses/create',
        method: 'POST',
        body: course,
      }),
    }),
    getCourseById: builder.query<Course, number>({
      query: (id) => ({
        url: `/courses/${id}`,
        method: 'GET',
      }),
    }),
    getAllCourse: builder.query<Course[], void>({
      query: () => ({
        url: '/courses',
        method: 'GET',
      }),
    }),
    filterCourses: builder.query<Course[], Partial<Course>>({
      query: (filter) => ({
        url: '/users',
        method: 'GET',
        params: filter,
      }),
    }),
    deleteCourseById: builder.mutation<Course, number>({
      query: (id) => ({
        url: `/courses/${id}/delete`,
        method: 'DELETE',
      }),
    }),
    updateCourseById: builder.mutation<Course, Partial<Course>>({
      query: (course) => ({
        url: `/courses/${course.id}/update`,
        method: 'PUT',
        body: course,
      }),
    }),
  }),
});

export const {
  useCreateCourseMutation,
  useGetCourseByIdQuery,
  useGetAllCourseQuery,
  useDeleteCourseByIdMutation,
  useUpdateCourseByIdMutation,
  useFilterCoursesQuery,
} = courseApi;
