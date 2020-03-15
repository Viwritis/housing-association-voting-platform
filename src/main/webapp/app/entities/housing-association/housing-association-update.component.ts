import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IHousingAssociation, HousingAssociation } from 'app/shared/model/housing-association.model';
import { HousingAssociationService } from './housing-association.service';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location/location.service';

@Component({
  selector: 'jhi-housing-association-update',
  templateUrl: './housing-association-update.component.html'
})
export class HousingAssociationUpdateComponent implements OnInit {
  isSaving = false;
  locations: ILocation[] = [];

  editForm = this.fb.group({
    id: [],
    housingAssociationName: [null, [Validators.required]],
    location: []
  });

  constructor(
    protected housingAssociationService: HousingAssociationService,
    protected locationService: LocationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ housingAssociation }) => {
      this.updateForm(housingAssociation);

      this.locationService
        .query({ filter: 'housingassociation-is-null' })
        .pipe(
          map((res: HttpResponse<ILocation[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ILocation[]) => {
          if (!housingAssociation.location || !housingAssociation.location.id) {
            this.locations = resBody;
          } else {
            this.locationService
              .find(housingAssociation.location.id)
              .pipe(
                map((subRes: HttpResponse<ILocation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ILocation[]) => (this.locations = concatRes));
          }
        });
    });
  }

  updateForm(housingAssociation: IHousingAssociation): void {
    this.editForm.patchValue({
      id: housingAssociation.id,
      housingAssociationName: housingAssociation.housingAssociationName,
      location: housingAssociation.location
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const housingAssociation = this.createFromForm();
    if (housingAssociation.id !== undefined) {
      this.subscribeToSaveResponse(this.housingAssociationService.update(housingAssociation));
    } else {
      this.subscribeToSaveResponse(this.housingAssociationService.create(housingAssociation));
    }
  }

  private createFromForm(): IHousingAssociation {
    return {
      ...new HousingAssociation(),
      id: this.editForm.get(['id'])!.value,
      housingAssociationName: this.editForm.get(['housingAssociationName'])!.value,
      location: this.editForm.get(['location'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHousingAssociation>>): void {
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

  trackById(index: number, item: ILocation): any {
    return item.id;
  }
}
