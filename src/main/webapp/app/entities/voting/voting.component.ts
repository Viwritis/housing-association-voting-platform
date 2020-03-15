import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVoting } from 'app/shared/model/voting.model';
import { VotingService } from './voting.service';
import { VotingDeleteDialogComponent } from './voting-delete-dialog.component';

@Component({
  selector: 'jhi-voting',
  templateUrl: './voting.component.html'
})
export class VotingComponent implements OnInit, OnDestroy {
  votings?: IVoting[];
  eventSubscriber?: Subscription;

  constructor(protected votingService: VotingService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.votingService.query().subscribe((res: HttpResponse<IVoting[]>) => (this.votings = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInVotings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVoting): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInVotings(): void {
    this.eventSubscriber = this.eventManager.subscribe('votingListModification', () => this.loadAll());
  }

  delete(voting: IVoting): void {
    const modalRef = this.modalService.open(VotingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.voting = voting;
  }
}
