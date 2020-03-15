import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHousingAssociation } from 'app/shared/model/housing-association.model';

type EntityResponseType = HttpResponse<IHousingAssociation>;
type EntityArrayResponseType = HttpResponse<IHousingAssociation[]>;

@Injectable({ providedIn: 'root' })
export class HousingAssociationService {
  public resourceUrl = SERVER_API_URL + 'api/housing-associations';

  constructor(protected http: HttpClient) {}

  create(housingAssociation: IHousingAssociation): Observable<EntityResponseType> {
    return this.http.post<IHousingAssociation>(this.resourceUrl, housingAssociation, { observe: 'response' });
  }

  update(housingAssociation: IHousingAssociation): Observable<EntityResponseType> {
    return this.http.put<IHousingAssociation>(this.resourceUrl, housingAssociation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHousingAssociation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHousingAssociation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
