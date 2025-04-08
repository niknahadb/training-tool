import type { Identifiable } from '../identifiable/identifiable';
import type { Lesson } from '../lesson/lesson';

export interface Module extends Identifiable {
  name: string;
  description: string;
  courseId: number;
  lessons: Lesson[];
}
