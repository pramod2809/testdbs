import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IUSER_REQUEST } from 'app/shared/model/user-request.model';

type EntityResponseType = HttpResponse<IUSER_REQUEST>;
type EntityArrayResponseType = HttpResponse<IUSER_REQUEST[]>;

@Injectable({ providedIn: 'root' })
export class USER_REQUESTService {
  public resourceUrl = SERVER_API_URL + 'api/user-requests';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/user-requests';

  constructor(protected http: HttpClient) {}

  create(uSER_REQUEST: IUSER_REQUEST): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(uSER_REQUEST);
    return this.http
      .post<IUSER_REQUEST>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(uSER_REQUEST: IUSER_REQUEST): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(uSER_REQUEST);
    return this.http
      .put<IUSER_REQUEST>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUSER_REQUEST>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUSER_REQUEST[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUSER_REQUEST[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(uSER_REQUEST: IUSER_REQUEST): IUSER_REQUEST {
    const copy: IUSER_REQUEST = Object.assign({}, uSER_REQUEST, {
      submitted_date:
        uSER_REQUEST.submitted_date && uSER_REQUEST.submitted_date.isValid() ? uSER_REQUEST.submitted_date.format(DATE_FORMAT) : undefined,
      approval_date:
        uSER_REQUEST.approval_date && uSER_REQUEST.approval_date.isValid() ? uSER_REQUEST.approval_date.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.submitted_date = res.body.submitted_date ? moment(res.body.submitted_date) : undefined;
      res.body.approval_date = res.body.approval_date ? moment(res.body.approval_date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((uSER_REQUEST: IUSER_REQUEST) => {
        uSER_REQUEST.submitted_date = uSER_REQUEST.submitted_date ? moment(uSER_REQUEST.submitted_date) : undefined;
        uSER_REQUEST.approval_date = uSER_REQUEST.approval_date ? moment(uSER_REQUEST.approval_date) : undefined;
      });
    }
    return res;
  }
}
