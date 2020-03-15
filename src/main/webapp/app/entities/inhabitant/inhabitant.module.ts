import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HousingAssociationVotingPlatformSharedModule } from 'app/shared/shared.module';
import { InhabitantComponent } from './inhabitant.component';
import { InhabitantDetailComponent } from './inhabitant-detail.component';
import { InhabitantUpdateComponent } from './inhabitant-update.component';
import { InhabitantDeleteDialogComponent } from './inhabitant-delete-dialog.component';
import { inhabitantRoute } from './inhabitant.route';

@NgModule({
  imports: [HousingAssociationVotingPlatformSharedModule, RouterModule.forChild(inhabitantRoute)],
  declarations: [InhabitantComponent, InhabitantDetailComponent, InhabitantUpdateComponent, InhabitantDeleteDialogComponent],
  entryComponents: [InhabitantDeleteDialogComponent]
})
export class HousingAssociationVotingPlatformInhabitantModule {}
