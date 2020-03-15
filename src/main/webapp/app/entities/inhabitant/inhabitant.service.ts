import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInhabitant } from 'app/shared/model/inhabitant.model';

type EntityResponseType = HttpResponse<IInhabitant>;
type EntityArrayResponseType = HttpResponse<IInhabitant[]>;

@Injectable({ providedIn: 'root' })
export class InhabitantService {
  public resourceUrl = SERVER_API_URL + 'api/inhabitants';

  constructor(protected http: HttpClient) {}

  create(inhabitant: IInhabitant): Observable<EntityResponseType> {
    return this.http.post<IInhabitant>(this.resourceUrl, inhabitant, { observe: 'response' });
  }

  update(inhabitant: IInhabitant): Observable<EntityResponseType> {
    return this.http.put<IInhabitant>(this.resourceUrl, inhabitant, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInhabitant>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInhabitant[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
