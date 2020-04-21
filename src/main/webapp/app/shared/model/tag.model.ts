import { IGrupoTag } from 'app/shared/model/grupo-tag.model';
import { IMateria } from 'app/shared/model/materia.model';

export interface ITag {
  id?: number;
  nome?: string;
  grupoTag?: IGrupoTag;
  materias?: IMateria[];
}

export class Tag implements ITag {
  constructor(public id?: number, public nome?: string, public grupoTag?: IGrupoTag, public materias?: IMateria[]) {}
}
