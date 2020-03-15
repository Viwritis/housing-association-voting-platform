import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HousingAssociationVotingPlatformSharedModule } from 'app/shared/shared.module';
import { VotingComponent } from './voting.component';
import { VotingDetailComponent } from './voting-detail.component';
import { VotingUpdateComponent } from './voting-update.component';
import { VotingDeleteDialogComponent } from './voting-delete-dialog.component';
import { votingRoute } from './voting.route';

@NgModule({
  imports: [HousingAssociationVotingPlatformSharedModule, RouterModule.forChild(votingRoute)],
  declarations: [VotingComponent, VotingDetailComponent, VotingUpdateComponent, VotingDeleteDialogComponent],
  entryComponents: [VotingDeleteDialogComponent]
})
export class HousingAssociationVotingPlatformVotingModule {}
