import { IMateria } from 'app/shared/model/materia.model';
import { IRevista } from 'app/shared/model/revista.model';

export interface ISecao {
  id?: number;
  titulo?: string;
  descricao?: string;
  materias?: IMateria[];
  revista?: IRevista;
}

export class Secao implements ISecao {
  constructor(
    public id?: number,
    public titulo?: string,
    public descricao?: string,
    public materias?: IMateria[],
    public revista?: IRevista
  ) {}
}
