import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'housing-association',
        loadChildren: () =>
          import('./housing-association/housing-association.module').then(m => m.HousingAssociationVotingPlatformHousingAssociationModule)
      },
      {
        path: 'inhabitant',
        loadChildren: () => import('./inhabitant/inhabitant.module').then(m => m.HousingAssociationVotingPlatformInhabitantModule)
      },
      {
        path: 'conclusion',
        loadChildren: () => import('./conclusion/conclusion.module').then(m => m.HousingAssociationVotingPlatformConclusionModule)
      },
      {
        path: 'voting',
        loadChildren: () => import('./voting/voting.module').then(m => m.HousingAssociationVotingPlatformVotingModule)
      },
      {
        path: 'news',
        loadChildren: () => import('./news/news.module').then(m => m.HousingAssociationVotingPlatformNewsModule)
      },
      {
        path: 'region',
        loadChildren: () => import('./region/region.module').then(m => m.HousingAssociationVotingPlatformRegionModule)
      },
      {
        path: 'country',
        loadChildren: () => import('./country/country.module').then(m => m.HousingAssociationVotingPlatformCountryModule)
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.HousingAssociationVotingPlatformLocationModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class HousingAssociationVotingPlatformEntityModule {}
