import { configureStore } from '@reduxjs/toolkit';

import { authApi } from './apis/authApi';
import { cohortApi } from './apis/cohortApi';
import { courseApi } from './apis/courseApi';
import { courseCollectionApi } from './apis/courseCollectionApi';
import { courseProgressApi } from './apis/courseProgressApi';
import { lessonApi } from './apis/lessonApi';
import { programApi } from './apis/programApi';
import { teamApi } from './apis/teamApi';
import { userApi } from './apis/userApi';
import authReducer from './slices/authSlice';

export const store = configureStore({
  reducer: {
    [authApi.reducerPath]: authApi.reducer,
    [cohortApi.reducerPath]: cohortApi.reducer,
    [programApi.reducerPath]: programApi.reducer,
    [teamApi.reducerPath]: teamApi.reducer,
    [userApi.reducerPath]: userApi.reducer,
    [courseApi.reducerPath]: courseApi.reducer,
    [courseProgressApi.reducerPath]: courseProgressApi.reducer,
    [courseCollectionApi.reducerPath]: courseCollectionApi.reducer,
    [lessonApi.reducerPath]: lessonApi.reducer,
    auth: authReducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware()
      .concat(authApi.middleware)
      .concat(cohortApi.middleware)
      .concat(programApi.middleware)
      .concat(teamApi.middleware)
      .concat(userApi.middleware)
      .concat(courseProgressApi.middleware)
      .concat(courseCollectionApi.middleware)
      .concat(courseApi.middleware)
      .concat(lessonApi.middleware),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export { useLoginMutation } from './apis/authApi';
export {
  useCreateCohortMutation,
  useDeleteCohortByIdMutation,
  useGetAllCohortsQuery,
  useGetCohortByIdQuery,
  useGetCurrentCohortQuery,
  useUpdateCohortByIdMutation,
} from './apis/cohortApi';
export {
  useCreateCourseMutation,
  useDeleteCourseByIdMutation,
  useFilterCoursesQuery,
  useGetAllCourseQuery,
  useGetCourseByIdQuery,
  useUpdateCourseByIdMutation,
} from './apis/courseApi';
export {
  useAddCourseToCourseCollectionMutation,
  useCreateCourseCollectionMutation,
  useDeleteCourseCollectionByIdMutation,
  useGetAllCourseCollectionQuery,
  useGetCourseCollectionByIdQuery,
  useUpdateCourseCollectionByIdMutation,
} from './apis/courseCollectionApi';
export {
  useCreateCourseProgressMutation,
  useGetAllCourseProgressQuery,
  useUpdateCourseProgressByIdMutation,
} from './apis/courseProgressApi';
export {
  useCreateLessonMutation,
  useDeleteLessonByIdMutation,
  useGetAllLessonsQuery,
  useGetLessonByIdQuery,
  useUpdateLessonByIdMutation,
} from './apis/lessonApi';
export {
  useCreateProgramMutation,
  useGetAllProgramsQuery,
  useGetProgramByIdQuery,
} from './apis/programApi';
export {
  useCreateTeamMutation,
  useFilterTeamsQuery,
  useGetAllTeamsQuery,
  useGetTeamByIdQuery,
  useUpdateTeamMutation,
} from './apis/teamApi';
export {
  useCreateUserMutation,
  useFilterUsersQuery,
  useGetAllUsersQuery,
  useGetUserByIdQuery,
  useUpdateUserMutation,
} from './apis/userApi';
