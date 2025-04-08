import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

import type { AuthState, LoginRequest } from '@/types/user/user';

export const authApi = createApi({
  baseQuery: fetchBaseQuery({
    baseUrl: 'http://localhost:8010',
  }),
  endpoints: (builder) => ({
    login: builder.mutation<AuthState, LoginRequest>({
      query: (credentials) => ({
        url: 'api-token-auth',
        method: 'POST',
        body: credentials,
      }),
    }),
  }),
});

export const { useLoginMutation } = authApi;
