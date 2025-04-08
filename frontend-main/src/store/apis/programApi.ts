import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

import type { Program } from '@/types/program/program';

export const programApi = createApi({
  reducerPath: 'program',
  baseQuery: fetchBaseQuery({
    baseUrl: 'http://localhost:8080',
  }),
  endpoints: (builder) => ({
    createProgram: builder.mutation<Program, Partial<Program>>({
      query: (cohort) => ({
        url: '/programs/create',
        method: 'POST',
        body: cohort,
      }),
    }),
    getProgramById: builder.query<Program, number>({
      query: (id) => ({
        url: `/programs/${id}`,
        method: 'GET',
      }),
    }),
    getAllPrograms: builder.query<Program[], void>({
      query: () => ({
        url: '/programs',
        method: 'GET',
      }),
    }),
  }),
});

export const {
  useCreateProgramMutation,
  useGetAllProgramsQuery,
  useGetProgramByIdQuery,
} = programApi;
