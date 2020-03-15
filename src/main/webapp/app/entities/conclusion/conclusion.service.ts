import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConclusion } from 'app/shared/model/conclusion.model';

type EntityResponseType = HttpResponse<IConclusion>;
type EntityArrayResponseType = HttpResponse<IConclusion[]>;

@Injectable({ providedIn: 'root' })
export class ConclusionService {
  public resourceUrl = SERVER_API_URL + 'api/conclusions';

  constructor(protected http: HttpClient) {}

  create(conclusion: IConclusion): Observable<EntityResponseType> {
    return this.http.post<IConclusion>(this.resourceUrl, conclusion, { observe: 'response' });
  }

  update(conclusion: IConclusion): Observable<EntityResponseType> {
    return this.http.put<IConclusion>(this.resourceUrl, conclusion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConclusion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConclusion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
