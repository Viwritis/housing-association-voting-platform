import { IConclusion } from 'app/shared/model/conclusion.model';
import { INews } from 'app/shared/model/news.model';
import { IHousingAssociation } from 'app/shared/model/housing-association.model';

export interface IInhabitant {
  id?: number;
  phoneNumber?: string;
  conclusions?: IConclusion[];
  news?: INews[];
  housingAssociation?: IHousingAssociation;
}

export class Inhabitant implements IInhabitant {
  constructor(
    public id?: number,
    public phoneNumber?: string,
    public conclusions?: IConclusion[],
    public news?: INews[],
    public housingAssociation?: IHousingAssociation
  ) {}
}
