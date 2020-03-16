import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IConclusion, Conclusion } from 'app/shared/model/conclusion.model';
import { ConclusionService } from './conclusion.service';
import { IVoting } from 'app/shared/model/voting.model';
import { VotingService } from 'app/entities/voting/voting.service';
import { IInhabitant } from 'app/shared/model/inhabitant.model';
import { InhabitantService } from 'app/entities/inhabitant/inhabitant.service';

type SelectableEntity = IVoting | IInhabitant;

@Component({
  selector: 'jhi-conclusion-update',
  templateUrl: './conclusion-update.component.html'
})
export class ConclusionUpdateComponent implements OnInit {
  isSaving = false;
  votings: IVoting[] = [];
  inhabitants: IInhabitant[] = [];

  editForm = this.fb.group({
    id: [],
    conclusionName: [null, [Validators.required]],
    conclusionContent: [null, [Validators.required]],
    creationDate: [],
    modificationDate: [],
    voting: [],
    inhabitant: []
  });

  constructor(
    protected conclusionService: ConclusionService,
    protected votingService: VotingService,
    protected inhabitantService: InhabitantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conclusion }) => {
      if (!conclusion.id) {
        const today = moment().startOf('day');
        conclusion.creationDate = today;
        conclusion.modificationDate = today;
      }

      this.updateForm(conclusion);

      this.votingService
        .query({ filter: 'conclusion-is-null' })
        .pipe(
          map((res: HttpResponse<IVoting[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IVoting[]) => {
          if (!conclusion.voting || !conclusion.voting.id) {
            this.votings = resBody;
          } else {
            this.votingService
              .find(conclusion.voting.id)
              .pipe(
                map((subRes: HttpResponse<IVoting>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IVoting[]) => (this.votings = concatRes));
          }
        });

      this.inhabitantService.query().subscribe((res: HttpResponse<IInhabitant[]>) => (this.inhabitants = res.body || []));
    });
  }

  updateForm(conclusion: IConclusion): void {
    this.editForm.patchValue({
      id: conclusion.id,
      conclusionName: conclusion.conclusionName,
      conclusionContent: conclusion.conclusionContent,
      creationDate: conclusion.creationDate ? conclusion.creationDate.format(DATE_TIME_FORMAT) : null,
      modificationDate: conclusion.modificationDate ? conclusion.modificationDate.format(DATE_TIME_FORMAT) : null,
      voting: conclusion.voting,
      inhabitant: conclusion.inhabitant
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conclusion = this.createFromForm();
    if (conclusion.id !== undefined) {
      this.subscribeToSaveResponse(this.conclusionService.update(conclusion));
    } else {
      this.subscribeToSaveResponse(this.conclusionService.create(conclusion));
    }
  }

  private createFromForm(): IConclusion {
    return {
      ...new Conclusion(),
      id: this.editForm.get(['id'])!.value,
      conclusionName: this.editForm.get(['conclusionName'])!.value,
      conclusionContent: this.editForm.get(['conclusionContent'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value
        ? moment(this.editForm.get(['creationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modificationDate: this.editForm.get(['modificationDate'])!.value
        ? moment(this.editForm.get(['modificationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      voting: this.editForm.get(['voting'])!.value,
      inhabitant: this.editForm.get(['inhabitant'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConclusion>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
