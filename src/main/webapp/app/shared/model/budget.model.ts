import { IIncome } from 'app/shared/model/income.model';
import { IOutcome } from 'app/shared/model/outcome.model';

export interface IBudget {
  id?: string;
  title?: string;
  description?: string;
  incomes?: IIncome[];
  outcomes?: IOutcome[];
}

export const defaultValue: Readonly<IBudget> = {};
