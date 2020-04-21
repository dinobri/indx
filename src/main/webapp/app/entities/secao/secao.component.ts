import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISecao } from 'app/shared/model/secao.model';
import { SecaoService } from './secao.service';
import { SecaoDeleteDialogComponent } from './secao-delete-dialog.component';

@Component({
  selector: 'jhi-secao',
  templateUrl: './secao.component.html'
})
export class SecaoComponent implements OnInit, OnDestroy {
  secaos?: ISecao[];
  eventSubscriber?: Subscription;

  constructor(protected secaoService: SecaoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.secaoService.query().subscribe((res: HttpResponse<ISecao[]>) => (this.secaos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSecaos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISecao): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSecaos(): void {
    this.eventSubscriber = this.eventManager.subscribe('secaoListModification', () => this.loadAll());
  }

  delete(secao: ISecao): void {
    const modalRef = this.modalService.open(SecaoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.secao = secao;
  }
}
