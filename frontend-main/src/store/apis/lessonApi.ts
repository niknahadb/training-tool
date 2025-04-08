import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

import type { Lesson } from '@/types/lesson/lesson';

export const lessonApi = createApi({
  reducerPath: 'lessons',
  baseQuery: fetchBaseQuery({
    baseUrl: 'http://localhost:8080',
  }),
  endpoints: (builder) => ({
    createLesson: builder.mutation<Lesson, Partial<Lesson>>({
      query: (lesson) => ({
        url: '/lessons/create',
        method: 'POST',
        body: lesson,
      }),
    }),
    getLessonById: builder.query<Lesson, number>({
      query: (id) => ({
        url: `/lessons/${id}`,
        method: 'GET',
      }),
    }),
    getAllLessons: builder.query<Lesson[], void>({
      query: () => ({
        url: '/lessons',
        method: 'GET',
      }),
    }),
    deleteLessonById: builder.mutation<Lesson, number>({
      query: (id) => ({
        url: `/lessons/${id}/delete`,
        method: 'DELETE',
      }),
    }),
    updateLessonById: builder.mutation<Lesson, Partial<Lesson>>({
      query: (lesson) => ({
        url: `/lessons/${lesson.id}/update`,
        method: 'PUT',
        body: lesson,
      }),
    }),
  }),
});

export const {
  useCreateLessonMutation,
  useGetLessonByIdQuery,
  useGetAllLessonsQuery,
  useDeleteLessonByIdMutation,
  useUpdateLessonByIdMutation,
} = lessonApi;
