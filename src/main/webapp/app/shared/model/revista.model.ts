import { ISecao } from 'app/shared/model/secao.model';
import { IEdicao } from 'app/shared/model/edicao.model';
import { Periodicidade } from 'app/shared/model/enumerations/periodicidade.model';

export interface IRevista {
  id?: number;
  nome?: string;
  descricao?: string;
  periodicidade?: Periodicidade;
  secaos?: ISecao[];
  edicaos?: IEdicao[];
}

export class Revista implements IRevista {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public periodicidade?: Periodicidade,
    public secaos?: ISecao[],
    public edicaos?: IEdicao[]
  ) {}
}
