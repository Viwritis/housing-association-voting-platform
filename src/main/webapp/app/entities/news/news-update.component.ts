import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { INews, News } from 'app/shared/model/news.model';
import { NewsService } from './news.service';
import { IInhabitant } from 'app/shared/model/inhabitant.model';
import { InhabitantService } from 'app/entities/inhabitant/inhabitant.service';

@Component({
  selector: 'jhi-news-update',
  templateUrl: './news-update.component.html'
})
export class NewsUpdateComponent implements OnInit {
  isSaving = false;
  inhabitants: IInhabitant[] = [];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    news: [null, [Validators.required]],
    creationDate: [],
    modificationDate: [],
    inhabitant: []
  });

  constructor(
    protected newsService: NewsService,
    protected inhabitantService: InhabitantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ news }) => {
      if (!news.id) {
        const today = moment().startOf('day');
        news.creationDate = today;
        news.modificationDate = today;
      }

      this.updateForm(news);

      this.inhabitantService.query().subscribe((res: HttpResponse<IInhabitant[]>) => (this.inhabitants = res.body || []));
    });
  }

  updateForm(news: INews): void {
    this.editForm.patchValue({
      id: news.id,
      title: news.title,
      news: news.news,
      creationDate: news.creationDate ? news.creationDate.format(DATE_TIME_FORMAT) : null,
      modificationDate: news.modificationDate ? news.modificationDate.format(DATE_TIME_FORMAT) : null,
      inhabitant: news.inhabitant
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const news = this.createFromForm();
    if (news.id !== undefined) {
      this.subscribeToSaveResponse(this.newsService.update(news));
    } else {
      this.subscribeToSaveResponse(this.newsService.create(news));
    }
  }

  private createFromForm(): INews {
    return {
      ...new News(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      news: this.editForm.get(['news'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value
        ? moment(this.editForm.get(['creationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      modificationDate: this.editForm.get(['modificationDate'])!.value
        ? moment(this.editForm.get(['modificationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      inhabitant: this.editForm.get(['inhabitant'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INews>>): void {
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

  trackById(index: number, item: IInhabitant): any {
    return item.id;
  }
}
