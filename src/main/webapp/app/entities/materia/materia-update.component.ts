import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMateria, Materia } from 'app/shared/model/materia.model';
import { MateriaService } from './materia.service';
import { ITag } from 'app/shared/model/tag.model';
import { TagService } from 'app/entities/tag/tag.service';
import { IAutor } from 'app/shared/model/autor.model';
import { AutorService } from 'app/entities/autor/autor.service';
import { ISecao } from 'app/shared/model/secao.model';
import { SecaoService } from 'app/entities/secao/secao.service';
import { IEdicao } from 'app/shared/model/edicao.model';
import { EdicaoService } from 'app/entities/edicao/edicao.service';

type SelectableEntity = ITag | IAutor | ISecao | IEdicao;

type SelectableManyToManyEntity = ITag | IAutor;

@Component({
  selector: 'jhi-materia-update',
  templateUrl: './materia-update.component.html'
})
export class MateriaUpdateComponent implements OnInit {
  isSaving = false;
  tags: ITag[] = [];
  autors: IAutor[] = [];
  secaos: ISecao[] = [];
  edicaos: IEdicao[] = [];

  editForm = this.fb.group({
    id: [],
    titulo: [],
    descricao: [],
    paginaInicial: [],
    paginaFinal: [],
    tags: [],
    autors: [],
    secao: [],
    edicao: []
  });

  constructor(
    protected materiaService: MateriaService,
    protected tagService: TagService,
    protected autorService: AutorService,
    protected secaoService: SecaoService,
    protected edicaoService: EdicaoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ materia }) => {
      this.updateForm(materia);

      this.tagService.query().subscribe((res: HttpResponse<ITag[]>) => (this.tags = res.body || []));

      this.autorService.query().subscribe((res: HttpResponse<IAutor[]>) => (this.autors = res.body || []));

      this.secaoService.query().subscribe((res: HttpResponse<ISecao[]>) => (this.secaos = res.body || []));

      this.edicaoService.query().subscribe((res: HttpResponse<IEdicao[]>) => (this.edicaos = res.body || []));
    });
  }

  updateForm(materia: IMateria): void {
    this.editForm.patchValue({
      id: materia.id,
      titulo: materia.titulo,
      descricao: materia.descricao,
      paginaInicial: materia.paginaInicial,
      paginaFinal: materia.paginaFinal,
      tags: materia.tags,
      autors: materia.autors,
      secao: materia.secao,
      edicao: materia.edicao
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const materia = this.createFromForm();
    if (materia.id !== undefined) {
      this.subscribeToSaveResponse(this.materiaService.update(materia));
    } else {
      this.subscribeToSaveResponse(this.materiaService.create(materia));
    }
  }

  private createFromForm(): IMateria {
    return {
      ...new Materia(),
      id: this.editForm.get(['id'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      paginaInicial: this.editForm.get(['paginaInicial'])!.value,
      paginaFinal: this.editForm.get(['paginaFinal'])!.value,
      tags: this.editForm.get(['tags'])!.value,
      autors: this.editForm.get(['autors'])!.value,
      secao: this.editForm.get(['secao'])!.value,
      edicao: this.editForm.get(['edicao'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMateria>>): void {
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

  getSelected(selectedVals: SelectableManyToManyEntity[], option: SelectableManyToManyEntity): SelectableManyToManyEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
