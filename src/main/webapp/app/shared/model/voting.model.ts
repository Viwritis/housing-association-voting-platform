import { Moment } from 'moment';

export interface IVoting {
  id?: number;
  startDate?: Moment;
  endDate?: Moment;
}

export class Voting implements IVoting {
  constructor(public id?: number, public startDate?: Moment, public endDate?: Moment) {}
}
