import { Moment } from 'moment';
import { IMateria } from 'app/shared/model/materia.model';
import { IRevista } from 'app/shared/model/revista.model';

export interface IEdicao {
  id?: number;
  numero?: number;
  dataPublicacao?: Moment;
  referencia?: string;
  materias?: IMateria[];
  revista?: IRevista;
}

export class Edicao implements IEdicao {
  constructor(
    public id?: number,
    public numero?: number,
    public dataPublicacao?: Moment,
    public referencia?: string,
    public materias?: IMateria[],
    public revista?: IRevista
  ) {}
}
