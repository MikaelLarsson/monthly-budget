import { IBudget } from 'app/shared/model//budget.model';

export interface IOutcome {
  id?: string;
  amount?: number;
  budget?: IBudget;
}

export const defaultValue: Readonly<IOutcome> = {};
