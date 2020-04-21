import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISecao, Secao } from 'app/shared/model/secao.model';
import { SecaoService } from './secao.service';
import { SecaoComponent } from './secao.component';
import { SecaoDetailComponent } from './secao-detail.component';
import { SecaoUpdateComponent } from './secao-update.component';

@Injectable({ providedIn: 'root' })
export class SecaoResolve implements Resolve<ISecao> {
  constructor(private service: SecaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISecao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((secao: HttpResponse<Secao>) => {
          if (secao.body) {
            return of(secao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Secao());
  }
}

export const secaoRoute: Routes = [
  {
    path: '',
    component: SecaoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'indxApp.secao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SecaoDetailComponent,
    resolve: {
      secao: SecaoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'indxApp.secao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SecaoUpdateComponent,
    resolve: {
      secao: SecaoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'indxApp.secao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SecaoUpdateComponent,
    resolve: {
      secao: SecaoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'indxApp.secao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
