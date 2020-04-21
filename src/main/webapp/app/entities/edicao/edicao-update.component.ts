import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEdicao, Edicao } from 'app/shared/model/edicao.model';
import { EdicaoService } from './edicao.service';
import { IRevista } from 'app/shared/model/revista.model';
import { RevistaService } from 'app/entities/revista/revista.service';

@Component({
  selector: 'jhi-edicao-update',
  templateUrl: './edicao-update.component.html'
})
export class EdicaoUpdateComponent implements OnInit {
  isSaving = false;
  revistas: IRevista[] = [];
  dataPublicacaoDp: any;

  editForm = this.fb.group({
    id: [],
    numero: [],
    dataPublicacao: [],
    referencia: [],
    revista: []
  });

  constructor(
    protected edicaoService: EdicaoService,
    protected revistaService: RevistaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ edicao }) => {
      this.updateForm(edicao);

      this.revistaService.query().subscribe((res: HttpResponse<IRevista[]>) => (this.revistas = res.body || []));
    });
  }

  updateForm(edicao: IEdicao): void {
    this.editForm.patchValue({
      id: edicao.id,
      numero: edicao.numero,
      dataPublicacao: edicao.dataPublicacao,
      referencia: edicao.referencia,
      revista: edicao.revista
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const edicao = this.createFromForm();
    if (edicao.id !== undefined) {
      this.subscribeToSaveResponse(this.edicaoService.update(edicao));
    } else {
      this.subscribeToSaveResponse(this.edicaoService.create(edicao));
    }
  }

  private createFromForm(): IEdicao {
    return {
      ...new Edicao(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      dataPublicacao: this.editForm.get(['dataPublicacao'])!.value,
      referencia: this.editForm.get(['referencia'])!.value,
      revista: this.editForm.get(['revista'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEdicao>>): void {
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
