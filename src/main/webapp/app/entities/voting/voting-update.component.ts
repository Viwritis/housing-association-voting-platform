import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVoting, Voting } from 'app/shared/model/voting.model';
import { VotingService } from './voting.service';

@Component({
  selector: 'jhi-voting-update',
  templateUrl: './voting-update.component.html'
})
export class VotingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]]
  });

  constructor(protected votingService: VotingService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voting }) => {
      if (!voting.id) {
        const today = moment().startOf('day');
        voting.startDate = today;
        voting.endDate = today;
      }

      this.updateForm(voting);
    });
  }

  updateForm(voting: IVoting): void {
    this.editForm.patchValue({
      id: voting.id,
      startDate: voting.startDate ? voting.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: voting.endDate ? voting.endDate.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const voting = this.createFromForm();
    if (voting.id !== undefined) {
      this.subscribeToSaveResponse(this.votingService.update(voting));
    } else {
      this.subscribeToSaveResponse(this.votingService.create(voting));
    }
  }

  private createFromForm(): IVoting {
    return {
      ...new Voting(),
      id: this.editForm.get(['id'])!.value,
      startDate: this.editForm.get(['startDate'])!.value ? moment(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate'])!.value ? moment(this.editForm.get(['endDate'])!.value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoting>>): void {
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
}
