import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HousingAssociationVotingPlatformSharedModule } from 'app/shared/shared.module';

import { ConfigurationComponent } from './configuration.component';

import { configurationRoute } from './configuration.route';

@NgModule({
  imports: [HousingAssociationVotingPlatformSharedModule, RouterModule.forChild([configurationRoute])],
  declarations: [ConfigurationComponent]
})
export class ConfigurationModule {}
