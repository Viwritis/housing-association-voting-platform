import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHousingAssociation } from 'app/shared/model/housing-association.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { HousingAssociationService } from './housing-association.service';
import { HousingAssociationDeleteDialogComponent } from './housing-association-delete-dialog.component';

@Component({
  selector: 'jhi-housing-association',
  templateUrl: './housing-association.component.html'
})
export class HousingAssociationComponent implements OnInit, OnDestroy {
  housingAssociations: IHousingAssociation[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected housingAssociationService: HousingAssociationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.housingAssociations = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.housingAssociationService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IHousingAssociation[]>) => this.paginateHousingAssociations(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.housingAssociations = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInHousingAssociations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IHousingAssociation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInHousingAssociations(): void {
    this.eventSubscriber = this.eventManager.subscribe('housingAssociationListModification', () => this.reset());
  }

  delete(housingAssociation: IHousingAssociation): void {
    const modalRef = this.modalService.open(HousingAssociationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.housingAssociation = housingAssociation;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateHousingAssociations(data: IHousingAssociation[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.housingAssociations.push(data[i]);
      }
    }
  }
}
