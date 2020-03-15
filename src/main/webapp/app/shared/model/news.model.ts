import { IInhabitant } from 'app/shared/model/inhabitant.model';

export interface INews {
  id?: number;
  title?: string;
  news?: string;
  inhabitant?: IInhabitant;
}

export class News implements INews {
  constructor(public id?: number, public title?: string, public news?: string, public inhabitant?: IInhabitant) {}
}
