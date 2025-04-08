import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

import type { Team } from '@/types/team/team';

export const teamApi = createApi({
  reducerPath: 'team',
  baseQuery: fetchBaseQuery({
    baseUrl: 'http://localhost:8080',
  }),
  endpoints: (builder) => ({
    createTeam: builder.mutation<Team, Partial<Team>>({
      query: (team) => ({
        url: '/teams/create',
        method: 'POST',
        body: team,
      }),
    }),
    updateTeam: builder.mutation<Team, Partial<Team> & { id: number }>({
      query: (team) => ({
        url: `/teams/${team.id}/update`,
        method: 'PUT',
        body: team,
      }),
    }),
    getTeamById: builder.query<Team, number>({
      query: (id) => ({
        url: `/teams/${id}`,
        method: 'GET',
      }),
    }),
    getAllTeams: builder.query<Team[], void>({
      query: () => ({
        url: '/teams',
        method: 'GET',
      }),
    }),
    filterTeams: builder.query<Team[], Partial<Team>>({
      query: (filter) => ({
        url: '/teams',
        method: 'GET',
        params: filter,
      }),
    }),
  }),
});

export const {
  useCreateTeamMutation,
  useGetAllTeamsQuery,
  useGetTeamByIdQuery,
  useFilterTeamsQuery,
  useUpdateTeamMutation,
} = teamApi;
