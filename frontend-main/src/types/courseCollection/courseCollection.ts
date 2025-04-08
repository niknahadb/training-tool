import type { Course } from '../course/course';
import type { Identifiable } from '../identifiable/identifiable';

export interface CourseCollection extends Identifiable {
  id: number;
  name: string;
  createDate: Date;
  modifyDate: Date;
  courses: Course[];
}
