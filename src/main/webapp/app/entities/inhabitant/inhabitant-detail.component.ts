import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInhabitant } from 'app/shared/model/inhabitant.model';

@Component({
  selector: 'jhi-inhabitant-detail',
  templateUrl: './inhabitant-detail.component.html'
})
export class InhabitantDetailComponent implements OnInit {
  inhabitant: IInhabitant | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inhabitant }) => (this.inhabitant = inhabitant));
  }

  previousState(): void {
    window.history.back();
  }
}
