import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRevista } from 'app/shared/model/revista.model';
import { RevistaService } from './revista.service';
import { RevistaDeleteDialogComponent } from './revista-delete-dialog.component';

@Component({
  selector: 'jhi-revista',
  templateUrl: './revista.component.html'
})
export class RevistaComponent implements OnInit, OnDestroy {
  revistas?: IRevista[];
  eventSubscriber?: Subscription;

  constructor(protected revistaService: RevistaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.revistaService.query().subscribe((res: HttpResponse<IRevista[]>) => (this.revistas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRevistas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRevista): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRevistas(): void {
    this.eventSubscriber = this.eventManager.subscribe('revistaListModification', () => this.loadAll());
  }

  delete(revista: IRevista): void {
    const modalRef = this.modalService.open(RevistaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.revista = revista;
  }
}
