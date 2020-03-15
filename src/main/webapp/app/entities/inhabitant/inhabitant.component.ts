import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInhabitant } from 'app/shared/model/inhabitant.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { InhabitantService } from './inhabitant.service';
import { InhabitantDeleteDialogComponent } from './inhabitant-delete-dialog.component';

@Component({
  selector: 'jhi-inhabitant',
  templateUrl: './inhabitant.component.html'
})
export class InhabitantComponent implements OnInit, OnDestroy {
  inhabitants: IInhabitant[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected inhabitantService: InhabitantService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.inhabitants = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.inhabitantService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IInhabitant[]>) => this.paginateInhabitants(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.inhabitants = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInhabitants();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInhabitant): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInhabitants(): void {
    this.eventSubscriber = this.eventManager.subscribe('inhabitantListModification', () => this.reset());
  }

  delete(inhabitant: IInhabitant): void {
    const modalRef = this.modalService.open(InhabitantDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.inhabitant = inhabitant;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateInhabitants(data: IInhabitant[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.inhabitants.push(data[i]);
      }
    }
  }
}
