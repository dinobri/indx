import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAutor } from 'app/shared/model/autor.model';
import { AutorService } from './autor.service';
import { AutorDeleteDialogComponent } from './autor-delete-dialog.component';

@Component({
  selector: 'jhi-autor',
  templateUrl: './autor.component.html'
})
export class AutorComponent implements OnInit, OnDestroy {
  autors?: IAutor[];
  eventSubscriber?: Subscription;

  constructor(protected autorService: AutorService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.autorService.query().subscribe((res: HttpResponse<IAutor[]>) => (this.autors = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAutors();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAutor): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAutors(): void {
    this.eventSubscriber = this.eventManager.subscribe('autorListModification', () => this.loadAll());
  }

  delete(autor: IAutor): void {
    const modalRef = this.modalService.open(AutorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.autor = autor;
  }
}
