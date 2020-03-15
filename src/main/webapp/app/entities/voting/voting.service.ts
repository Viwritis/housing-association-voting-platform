import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVoting } from 'app/shared/model/voting.model';

type EntityResponseType = HttpResponse<IVoting>;
type EntityArrayResponseType = HttpResponse<IVoting[]>;

@Injectable({ providedIn: 'root' })
export class VotingService {
  public resourceUrl = SERVER_API_URL + 'api/votings';

  constructor(protected http: HttpClient) {}

  create(voting: IVoting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(voting);
    return this.http
      .post<IVoting>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(voting: IVoting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(voting);
    return this.http
      .put<IVoting>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVoting>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVoting[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(voting: IVoting): IVoting {
    const copy: IVoting = Object.assign({}, voting, {
      startDate: voting.startDate && voting.startDate.isValid() ? voting.startDate.toJSON() : undefined,
      endDate: voting.endDate && voting.endDate.isValid() ? voting.endDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? moment(res.body.endDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((voting: IVoting) => {
        voting.startDate = voting.startDate ? moment(voting.startDate) : undefined;
        voting.endDate = voting.endDate ? moment(voting.endDate) : undefined;
      });
    }
    return res;
  }
}
