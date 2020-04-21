import { ITag } from 'app/shared/model/tag.model';

export interface IGrupoTag {
  id?: number;
  nome?: string;
  cor?: string;
  tags?: ITag[];
}

export class GrupoTag implements IGrupoTag {
  constructor(public id?: number, public nome?: string, public cor?: string, public tags?: ITag[]) {}
}
