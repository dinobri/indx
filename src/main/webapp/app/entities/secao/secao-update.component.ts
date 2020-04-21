import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISecao, Secao } from 'app/shared/model/secao.model';
import { SecaoService } from './secao.service';
import { IRevista } from 'app/shared/model/revista.model';
import { RevistaService } from 'app/entities/revista/revista.service';

@Component({
  selector: 'jhi-secao-update',
  templateUrl: './secao-update.component.html'
})
export class SecaoUpdateComponent implements OnInit {
  isSaving = false;
  revistas: IRevista[] = [];

  editForm = this.fb.group({
    id: [],
    titulo: [],
    descricao: [],
    revista: []
  });

  constructor(
    protected secaoService: SecaoService,
    protected revistaService: RevistaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ secao }) => {
      this.updateForm(secao);

      this.revistaService.query().subscribe((res: HttpResponse<IRevista[]>) => (this.revistas = res.body || []));
    });
  }

  updateForm(secao: ISecao): void {
    this.editForm.patchValue({
      id: secao.id,
      titulo: secao.titulo,
      descricao: secao.descricao,
      revista: secao.revista
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const secao = this.createFromForm();
    if (secao.id !== undefined) {
      this.subscribeToSaveResponse(this.secaoService.update(secao));
    } else {
      this.subscribeToSaveResponse(this.secaoService.create(secao));
    }
  }

  private createFromForm(): ISecao {
    return {
      ...new Secao(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      revista: this.editForm.get(['revista'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISecao>>): void {
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

  trackById(index: number, item: IRevista): any {
    return item.id;
  }
}
