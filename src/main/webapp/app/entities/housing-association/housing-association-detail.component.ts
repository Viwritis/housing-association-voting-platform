import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHousingAssociation } from 'app/shared/model/housing-association.model';

@Component({
  selector: 'jhi-housing-association-detail',
  templateUrl: './housing-association-detail.component.html'
})
export class HousingAssociationDetailComponent implements OnInit {
  housingAssociation: IHousingAssociation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ housingAssociation }) => (this.housingAssociation = housingAssociation));
  }

  previousState(): void {
    window.history.back();
  }
}
