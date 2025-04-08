import type { Identifiable } from '../identifiable/identifiable';
import type { Module } from '../module/module';

export interface Course extends Identifiable {
  name: string;
  description: string;
  coverImage: string;
  type: string;
  modules: Module[];
}
