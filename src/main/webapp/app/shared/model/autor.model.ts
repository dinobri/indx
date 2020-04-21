import { IMateria } from 'app/shared/model/materia.model';

export interface IAutor {
  id?: number;
  nome?: string;
  nomeReal?: string;
  alcunha?: string;
  materias?: IMateria[];
}

export class Autor implements IAutor {
  constructor(public id?: number, public nome?: string, public nomeReal?: string, public alcunha?: string, public materias?: IMateria[]) {}
}
