import { Moment } from 'moment';
import { IInhabitant } from 'app/shared/model/inhabitant.model';

export interface INews {
  id?: number;
  title?: string;
  news?: string;
  creationDate?: Moment;
  modificationDate?: Moment;
  inhabitant?: IInhabitant;
}

export class News implements INews {
  constructor(
    public id?: number,
    public title?: string,
    public news?: string,
    public creationDate?: Moment,
    public modificationDate?: Moment,
    public inhabitant?: IInhabitant
  ) {}
}
