export interface User {
  id: number;
  firstName: string;
  lastName: string;
  createDate: Date;
  modifyDate: Date;
  email: string;
  role: string;
  teamId: number;
  cohortId: number;
  programId: number;
}

export interface AuthState {
  token: string | null;
  user: User | null;
}

export interface LoginRequest {
  username: string;
  password: string;
}
