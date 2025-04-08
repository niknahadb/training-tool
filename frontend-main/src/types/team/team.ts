import type { Identifiable } from '../identifiable/identifiable';

export interface Team extends Identifiable {
  name: string;
  programId: number;
  cohortId: number;
  courseCollectionId: number;
}
