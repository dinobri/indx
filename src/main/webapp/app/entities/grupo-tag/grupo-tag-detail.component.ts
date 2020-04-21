import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGrupoTag } from 'app/shared/model/grupo-tag.model';

@Component({
  selector: 'jhi-grupo-tag-detail',
  templateUrl: './grupo-tag-detail.component.html'
})
export class GrupoTagDetailComponent implements OnInit {
  grupoTag: IGrupoTag | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ grupoTag }) => (this.grupoTag = grupoTag));
  }

  previousState(): void {
    window.history.back();
  }
}
