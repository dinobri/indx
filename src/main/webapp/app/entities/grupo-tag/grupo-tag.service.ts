import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGrupoTag } from 'app/shared/model/grupo-tag.model';

type EntityResponseType = HttpResponse<IGrupoTag>;
type EntityArrayResponseType = HttpResponse<IGrupoTag[]>;

@Injectable({ providedIn: 'root' })
export class GrupoTagService {
  public resourceUrl = SERVER_API_URL + 'api/grupo-tags';

  constructor(protected http: HttpClient) {}

  create(grupoTag: IGrupoTag): Observable<EntityResponseType> {
    return this.http.post<IGrupoTag>(this.resourceUrl, grupoTag, { observe: 'response' });
  }

  update(grupoTag: IGrupoTag): Observable<EntityResponseType> {
    return this.http.put<IGrupoTag>(this.resourceUrl, grupoTag, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGrupoTag>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGrupoTag[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
