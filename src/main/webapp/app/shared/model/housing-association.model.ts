import { ILocation } from 'app/shared/model/location.model';
import { IInhabitant } from 'app/shared/model/inhabitant.model';

export interface IHousingAssociation {
  id?: number;
  housingAssociationName?: string;
  location?: ILocation;
  inhabitants?: IInhabitant[];
}

export class HousingAssociation implements IHousingAssociation {
  constructor(
    public id?: number,
    public housingAssociationName?: string,
    public location?: ILocation,
    public inhabitants?: IInhabitant[]
  ) {}
}
