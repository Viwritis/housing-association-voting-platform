import { IVoting } from 'app/shared/model/voting.model';
import { IInhabitant } from 'app/shared/model/inhabitant.model';

export interface IConclusion {
  id?: number;
  conclusionName?: string;
  conclusionContent?: string;
  voting?: IVoting;
  inhabitant?: IInhabitant;
}

export class Conclusion implements IConclusion {
  constructor(
    public id?: number,
    public conclusionName?: string,
    public conclusionContent?: string,
    public voting?: IVoting,
    public inhabitant?: IInhabitant
  ) {}
}
