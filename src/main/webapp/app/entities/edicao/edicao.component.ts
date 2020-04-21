import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEdicao } from 'app/shared/model/edicao.model';
import { EdicaoService } from './edicao.service';
import { EdicaoDeleteDialogComponent } from './edicao-delete-dialog.component';

@Component({
  selector: 'jhi-edicao',
  templateUrl: './edicao.component.html'
})
export class EdicaoComponent implements OnInit, OnDestroy {
  edicaos?: IEdicao[];
  eventSubscriber?: Subscription;

  constructor(protected edicaoService: EdicaoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.edicaoService.query().subscribe((res: HttpResponse<IEdicao[]>) => (this.edicaos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEdicaos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEdicao): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEdicaos(): void {
    this.eventSubscriber = this.eventManager.subscribe('edicaoListModification', () => this.loadAll());
  }

  delete(edicao: IEdicao): void {
    const modalRef = this.modalService.open(EdicaoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.edicao = edicao;
  }
}
