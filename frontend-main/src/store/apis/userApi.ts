import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

import type { User } from '@/types/user/user';

export const userApi = createApi({
  reducerPath: 'user',
  baseQuery: fetchBaseQuery({
    baseUrl: 'http://localhost:8080',
  }),
  endpoints: (builder) => ({
    createUser: builder.mutation<User, Partial<User>>({
      query: (team) => ({
        url: '/users/create',
        method: 'POST',
        body: team,
      }),
    }),
    updateUser: builder.mutation<User, Partial<User> & { id: number }>({
      query: (team) => ({
        url: `/users/${team.id}/update`,
        method: 'PUT',
        body: team,
      }),
    }),
    getUserById: builder.query<User, number>({
      query: (id) => ({
        url: `/users/${id}`,
        method: 'GET',
      }),
    }),
    getAllUsers: builder.query<User[], void>({
      query: () => ({
        url: '/users',
        method: 'GET',
      }),
    }),
    filterUsers: builder.query<User[], Partial<User>>({
      query: (filter) => ({
        url: '/users',
        method: 'GET',
        params: filter,
      }),
    }),
  }),
});

export const {
  useCreateUserMutation,
  useGetAllUsersQuery,
  useFilterUsersQuery,
  useGetUserByIdQuery,
  useUpdateUserMutation,
} = userApi;
