import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUSER_REQUEST, USER_REQUEST } from 'app/shared/model/user-request.model';
import { USER_REQUESTService } from './user-request.service';
import { USER_REQUESTComponent } from './user-request.component';
import { USER_REQUESTDetailComponent } from './user-request-detail.component';
import { USER_REQUESTUpdateComponent } from './user-request-update.component';

@Injectable({ providedIn: 'root' })
export class USER_REQUESTResolve implements Resolve<IUSER_REQUEST> {
  constructor(private service: USER_REQUESTService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUSER_REQUEST> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((uSER_REQUEST: HttpResponse<USER_REQUEST>) => {
          if (uSER_REQUEST.body) {
            return of(uSER_REQUEST.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new USER_REQUEST());
  }
}

export const uSER_REQUESTRoute: Routes = [
  {
    path: '',
    component: USER_REQUESTComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'managementsystemApp.uSER_REQUEST.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: USER_REQUESTDetailComponent,
    resolve: {
      uSER_REQUEST: USER_REQUESTResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'managementsystemApp.uSER_REQUEST.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: USER_REQUESTUpdateComponent,
    resolve: {
      uSER_REQUEST: USER_REQUESTResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'managementsystemApp.uSER_REQUEST.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: USER_REQUESTUpdateComponent,
    resolve: {
      uSER_REQUEST: USER_REQUESTResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'managementsystemApp.uSER_REQUEST.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
