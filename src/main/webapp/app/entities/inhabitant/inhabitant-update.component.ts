import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInhabitant, Inhabitant } from 'app/shared/model/inhabitant.model';
import { InhabitantService } from './inhabitant.service';
import { IHousingAssociation } from 'app/shared/model/housing-association.model';
import { HousingAssociationService } from 'app/entities/housing-association/housing-association.service';

@Component({
  selector: 'jhi-inhabitant-update',
  templateUrl: './inhabitant-update.component.html'
})
export class InhabitantUpdateComponent implements OnInit {
  isSaving = false;
  housingassociations: IHousingAssociation[] = [];

  editForm = this.fb.group({
    id: [],
    phoneNumber: [],
    housingAssociation: []
  });

  constructor(
    protected inhabitantService: InhabitantService,
    protected housingAssociationService: HousingAssociationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inhabitant }) => {
      this.updateForm(inhabitant);

      this.housingAssociationService
        .query()
        .subscribe((res: HttpResponse<IHousingAssociation[]>) => (this.housingassociations = res.body || []));
    });
  }

  updateForm(inhabitant: IInhabitant): void {
    this.editForm.patchValue({
      id: inhabitant.id,
      phoneNumber: inhabitant.phoneNumber,
      housingAssociation: inhabitant.housingAssociation
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inhabitant = this.createFromForm();
    if (inhabitant.id !== undefined) {
      this.subscribeToSaveResponse(this.inhabitantService.update(inhabitant));
    } else {
      this.subscribeToSaveResponse(this.inhabitantService.create(inhabitant));
    }
  }

  private createFromForm(): IInhabitant {
    return {
      ...new Inhabitant(),
      id: this.editForm.get(['id'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      housingAssociation: this.editForm.get(['housingAssociation'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInhabitant>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IHousingAssociation): any {
    return item.id;
  }
}
