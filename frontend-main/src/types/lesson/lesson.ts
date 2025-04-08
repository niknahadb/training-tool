import type { Identifiable } from '../identifiable/identifiable';

export interface Lesson extends Identifiable {
  name: string;
  description: string;
  moduleId: number;
  courseId: number;
  content: string;
}
