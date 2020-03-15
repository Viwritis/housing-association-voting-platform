import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHousingAssociation, HousingAssociation } from 'app/shared/model/housing-association.model';
import { HousingAssociationService } from './housing-association.service';
import { HousingAssociationComponent } from './housing-association.component';
import { HousingAssociationDetailComponent } from './housing-association-detail.component';
import { HousingAssociationUpdateComponent } from './housing-association-update.component';

@Injectable({ providedIn: 'root' })
export class HousingAssociationResolve implements Resolve<IHousingAssociation> {
  constructor(private service: HousingAssociationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHousingAssociation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((housingAssociation: HttpResponse<HousingAssociation>) => {
          if (housingAssociation.body) {
            return of(housingAssociation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HousingAssociation());
  }
}

export const housingAssociationRoute: Routes = [
  {
    path: '',
    component: HousingAssociationComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'housingAssociationVotingPlatformApp.housingAssociation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HousingAssociationDetailComponent,
    resolve: {
      housingAssociation: HousingAssociationResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'housingAssociationVotingPlatformApp.housingAssociation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HousingAssociationUpdateComponent,
    resolve: {
      housingAssociation: HousingAssociationResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'housingAssociationVotingPlatformApp.housingAssociation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HousingAssociationUpdateComponent,
    resolve: {
      housingAssociation: HousingAssociationResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'housingAssociationVotingPlatformApp.housingAssociation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
