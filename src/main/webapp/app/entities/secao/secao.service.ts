import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISecao } from 'app/shared/model/secao.model';

type EntityResponseType = HttpResponse<ISecao>;
type EntityArrayResponseType = HttpResponse<ISecao[]>;

@Injectable({ providedIn: 'root' })
export class SecaoService {
  public resourceUrl = SERVER_API_URL + 'api/secaos';

  constructor(protected http: HttpClient) {}

  create(secao: ISecao): Observable<EntityResponseType> {
    return this.http.post<ISecao>(this.resourceUrl, secao, { observe: 'response' });
  }

  update(secao: ISecao): Observable<EntityResponseType> {
    return this.http.put<ISecao>(this.resourceUrl, secao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISecao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISecao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
