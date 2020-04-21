import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGrupoTag, GrupoTag } from 'app/shared/model/grupo-tag.model';
import { GrupoTagService } from './grupo-tag.service';

@Component({
  selector: 'jhi-grupo-tag-update',
  templateUrl: './grupo-tag-update.component.html'
})
export class GrupoTagUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [],
    cor: []
  });

  constructor(protected grupoTagService: GrupoTagService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ grupoTag }) => {
      this.updateForm(grupoTag);
    });
  }

  updateForm(grupoTag: IGrupoTag): void {
    this.editForm.patchValue({
      id: grupoTag.id,
      nome: grupoTag.nome,
      cor: grupoTag.cor
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const grupoTag = this.createFromForm();
    if (grupoTag.id !== undefined) {
      this.subscribeToSaveResponse(this.grupoTagService.update(grupoTag));
    } else {
      this.subscribeToSaveResponse(this.grupoTagService.create(grupoTag));
    }
  }

  private createFromForm(): IGrupoTag {
    return {
      ...new GrupoTag(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      cor: this.editForm.get(['cor'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrupoTag>>): void {
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
