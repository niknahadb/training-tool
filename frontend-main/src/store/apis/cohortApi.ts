import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

import type { Cohort } from '@/types/cohort/cohort';

export const cohortApi = createApi({
  reducerPath: 'cohort',
  baseQuery: fetchBaseQuery({
    baseUrl: 'http://localhost:8080',
  }),
  endpoints: (builder) => ({
    createCohort: builder.mutation<Cohort, Partial<Cohort>>({
      query: (cohort) => ({
        url: '/cohorts/create',
        method: 'POST',
        body: cohort,
      }),
    }),
    getCohortById: builder.query<Cohort, number>({
      query: (id) => ({
        url: `/cohorts/${id}`,
        method: 'GET',
      }),
    }),
    getAllCohorts: builder.query<Cohort[], void>({
      query: () => ({
        url: '/cohorts',
        method: 'GET',
      }),
    }),
    getCurrentCohort: builder.query<Cohort, void>({
      query: () => ({
        url: '/cohorts/current',
        method: 'GET',
      }),
    }),
    deleteCohortById: builder.mutation<Cohort, number>({
      query: (id) => ({
        url: `/cohorts/${id}/delete`,
        method: 'DELETE',
      }),
    }),
    updateCohortById: builder.mutation<Cohort, Partial<Cohort>>({
      query: (cohort) => ({
        url: `/cohorts/${cohort.id}/update`,
        method: 'PUT',
        body: cohort,
      }),
    }),
  }),
});

export const {
  useCreateCohortMutation,
  useGetCohortByIdQuery,
  useGetAllCohortsQuery,
  useGetCurrentCohortQuery,
  useDeleteCohortByIdMutation,
  useUpdateCohortByIdMutation,
} = cohortApi;
