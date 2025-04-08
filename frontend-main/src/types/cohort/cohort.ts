import type { Identifiable } from '../identifiable/identifiable';

export interface Cohort extends Identifiable {
  type: string;
  year: number;
}
