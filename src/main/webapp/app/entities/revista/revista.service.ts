import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRevista } from 'app/shared/model/revista.model';

type EntityResponseType = HttpResponse<IRevista>;
type EntityArrayResponseType = HttpResponse<IRevista[]>;

@Injectable({ providedIn: 'root' })
export class RevistaService {
  public resourceUrl = SERVER_API_URL + 'api/revistas';

  constructor(protected http: HttpClient) {}

  create(revista: IRevista): Observable<EntityResponseType> {
    return this.http.post<IRevista>(this.resourceUrl, revista, { observe: 'response' });
  }

  update(revista: IRevista): Observable<EntityResponseType> {
    return this.http.put<IRevista>(this.resourceUrl, revista, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRevista>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRevista[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
