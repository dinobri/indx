import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISecao } from 'app/shared/model/secao.model';

@Component({
  selector: 'jhi-secao-detail',
  templateUrl: './secao-detail.component.html'
})
export class SecaoDetailComponent implements OnInit {
  secao: ISecao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ secao }) => (this.secao = secao));
  }

  previousState(): void {
    window.history.back();
  }
}
