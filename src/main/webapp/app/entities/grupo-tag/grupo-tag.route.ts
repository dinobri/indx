import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGrupoTag, GrupoTag } from 'app/shared/model/grupo-tag.model';
import { GrupoTagService } from './grupo-tag.service';
import { GrupoTagComponent } from './grupo-tag.component';
import { GrupoTagDetailComponent } from './grupo-tag-detail.component';
import { GrupoTagUpdateComponent } from './grupo-tag-update.component';

@Injectable({ providedIn: 'root' })
export class GrupoTagResolve implements Resolve<IGrupoTag> {
  constructor(private service: GrupoTagService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGrupoTag> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((grupoTag: HttpResponse<GrupoTag>) => {
          if (grupoTag.body) {
            return of(grupoTag.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GrupoTag());
  }
}

export const grupoTagRoute: Routes = [
  {
    path: '',
    component: GrupoTagComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'indxApp.grupoTag.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GrupoTagDetailComponent,
    resolve: {
      grupoTag: GrupoTagResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'indxApp.grupoTag.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GrupoTagUpdateComponent,
    resolve: {
      grupoTag: GrupoTagResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'indxApp.grupoTag.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GrupoTagUpdateComponent,
    resolve: {
      grupoTag: GrupoTagResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'indxApp.grupoTag.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
