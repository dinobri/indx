import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRevista, Revista } from 'app/shared/model/revista.model';
import { RevistaService } from './revista.service';

@Component({
  selector: 'jhi-revista-update',
  templateUrl: './revista-update.component.html'
})
export class RevistaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [],
    descricao: [],
    periodicidade: []
  });

  constructor(protected revistaService: RevistaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ revista }) => {
      this.updateForm(revista);
    });
  }

  updateForm(revista: IRevista): void {
    this.editForm.patchValue({
      id: revista.id,
      nome: revista.nome,
      descricao: revista.descricao,
      periodicidade: revista.periodicidade
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const revista = this.createFromForm();
    if (revista.id !== undefined) {
      this.subscribeToSaveResponse(this.revistaService.update(revista));
    } else {
      this.subscribeToSaveResponse(this.revistaService.create(revista));
    }
  }

  private createFromForm(): IRevista {
    return {
      ...new Revista(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      periodicidade: this.editForm.get(['periodicidade'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRevista>>): void {
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
