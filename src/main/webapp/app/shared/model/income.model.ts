import { IBudget } from 'app/shared/model//budget.model';

export interface IIncome {
  id?: string;
  amount?: number;
  budget?: IBudget;
}

export const defaultValue: Readonly<IIncome> = {};
