import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInhabitant } from 'app/shared/model/inhabitant.model';
import { InhabitantService } from './inhabitant.service';

@Component({
  templateUrl: './inhabitant-delete-dialog.component.html'
})
export class InhabitantDeleteDialogComponent {
  inhabitant?: IInhabitant;

  constructor(
    protected inhabitantService: InhabitantService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.inhabitantService.delete(id).subscribe(() => {
      this.eventManager.broadcast('inhabitantListModification');
      this.activeModal.close();
    });
  }
}
