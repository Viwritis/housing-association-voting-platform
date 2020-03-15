import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HousingAssociationVotingPlatformSharedModule } from 'app/shared/shared.module';
import { HousingAssociationComponent } from './housing-association.component';
import { HousingAssociationDetailComponent } from './housing-association-detail.component';
import { HousingAssociationUpdateComponent } from './housing-association-update.component';
import { HousingAssociationDeleteDialogComponent } from './housing-association-delete-dialog.component';
import { housingAssociationRoute } from './housing-association.route';

@NgModule({
  imports: [HousingAssociationVotingPlatformSharedModule, RouterModule.forChild(housingAssociationRoute)],
  declarations: [
    HousingAssociationComponent,
    HousingAssociationDetailComponent,
    HousingAssociationUpdateComponent,
    HousingAssociationDeleteDialogComponent
  ],
  entryComponents: [HousingAssociationDeleteDialogComponent]
})
export class HousingAssociationVotingPlatformHousingAssociationModule {}
