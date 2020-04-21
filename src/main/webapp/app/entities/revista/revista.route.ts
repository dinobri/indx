import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRevista, Revista } from 'app/shared/model/revista.model';
import { RevistaService } from './revista.service';
import { RevistaComponent } from './revista.component';
import { RevistaDetailComponent } from './revista-detail.component';
import { RevistaUpdateComponent } from './revista-update.component';

@Injectable({ providedIn: 'root' })
export class RevistaResolve implements Resolve<IRevista> {
  constructor(private service: RevistaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRevista> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((revista: HttpResponse<Revista>) => {
          if (revista.body) {
            return of(revista.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Revista());
  }
}

export const revistaRoute: Routes = [
  {
    path: '',
    component: RevistaComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'indxApp.revista.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RevistaDetailComponent,
    resolve: {
      revista: RevistaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'indxApp.revista.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RevistaUpdateComponent,
    resolve: {
      revista: RevistaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'indxApp.revista.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RevistaUpdateComponent,
    resolve: {
      revista: RevistaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'indxApp.revista.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
