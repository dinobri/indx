import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRevista } from 'app/shared/model/revista.model';

@Component({
  selector: 'jhi-revista-detail',
  templateUrl: './revista-detail.component.html'
})
export class RevistaDetailComponent implements OnInit {
  revista: IRevista | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ revista }) => (this.revista = revista));
  }

  previousState(): void {
    window.history.back();
  }
}
