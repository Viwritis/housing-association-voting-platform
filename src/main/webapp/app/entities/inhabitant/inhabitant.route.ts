import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInhabitant, Inhabitant } from 'app/shared/model/inhabitant.model';
import { InhabitantService } from './inhabitant.service';
import { InhabitantComponent } from './inhabitant.component';
import { InhabitantDetailComponent } from './inhabitant-detail.component';
import { InhabitantUpdateComponent } from './inhabitant-update.component';

@Injectable({ providedIn: 'root' })
export class InhabitantResolve implements Resolve<IInhabitant> {
  constructor(private service: InhabitantService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInhabitant> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((inhabitant: HttpResponse<Inhabitant>) => {
          if (inhabitant.body) {
            return of(inhabitant.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Inhabitant());
  }
}

export const inhabitantRoute: Routes = [
  {
    path: '',
    component: InhabitantComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'housingAssociationVotingPlatformApp.inhabitant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InhabitantDetailComponent,
    resolve: {
      inhabitant: InhabitantResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'housingAssociationVotingPlatformApp.inhabitant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InhabitantUpdateComponent,
    resolve: {
      inhabitant: InhabitantResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'housingAssociationVotingPlatformApp.inhabitant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InhabitantUpdateComponent,
    resolve: {
      inhabitant: InhabitantResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'housingAssociationVotingPlatformApp.inhabitant.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
