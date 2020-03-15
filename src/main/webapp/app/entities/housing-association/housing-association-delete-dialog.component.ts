import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHousingAssociation } from 'app/shared/model/housing-association.model';
import { HousingAssociationService } from './housing-association.service';

@Component({
  templateUrl: './housing-association-delete-dialog.component.html'
})
export class HousingAssociationDeleteDialogComponent {
  housingAssociation?: IHousingAssociation;

  constructor(
    protected housingAssociationService: HousingAssociationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.housingAssociationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('housingAssociationListModification');
      this.activeModal.close();
    });
  }
}
