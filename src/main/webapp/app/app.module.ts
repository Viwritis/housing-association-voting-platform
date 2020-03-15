import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { HousingAssociationVotingPlatformSharedModule } from 'app/shared/shared.module';
import { HousingAssociationVotingPlatformCoreModule } from 'app/core/core.module';
import { HousingAssociationVotingPlatformAppRoutingModule } from './app-routing.module';
import { HousingAssociationVotingPlatformHomeModule } from './home/home.module';
import { HousingAssociationVotingPlatformEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    HousingAssociationVotingPlatformSharedModule,
    HousingAssociationVotingPlatformCoreModule,
    HousingAssociationVotingPlatformHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    HousingAssociationVotingPlatformEntityModule,
    HousingAssociationVotingPlatformAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent]
})
export class HousingAssociationVotingPlatformAppModule {}
