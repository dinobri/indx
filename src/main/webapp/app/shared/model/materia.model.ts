import { ITag } from 'app/shared/model/tag.model';
import { IAutor } from 'app/shared/model/autor.model';
import { ISecao } from 'app/shared/model/secao.model';
import { IEdicao } from 'app/shared/model/edicao.model';

export interface IMateria {
  id?: number;
  titulo?: string;
  descricao?: string;
  paginaInicial?: number;
  paginaFinal?: number;
  tags?: ITag[];
  autors?: IAutor[];
  secao?: ISecao;
  edicao?: IEdicao;
}

export class Materia implements IMateria {
  constructor(
    public id?: number,
    public titulo?: string,
    public descricao?: string,
    public paginaInicial?: number,
    public paginaFinal?: number,
    public tags?: ITag[],
    public autors?: IAutor[],
    public secao?: ISecao,
    public edicao?: IEdicao
  ) {}
}
