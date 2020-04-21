import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGrupoTag } from 'app/shared/model/grupo-tag.model';
import { GrupoTagService } from './grupo-tag.service';
import { GrupoTagDeleteDialogComponent } from './grupo-tag-delete-dialog.component';

@Component({
  selector: 'jhi-grupo-tag',
  templateUrl: './grupo-tag.component.html'
})
export class GrupoTagComponent implements OnInit, OnDestroy {
  grupoTags?: IGrupoTag[];
  eventSubscriber?: Subscription;

  constructor(protected grupoTagService: GrupoTagService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.grupoTagService.query().subscribe((res: HttpResponse<IGrupoTag[]>) => (this.grupoTags = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGrupoTags();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGrupoTag): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGrupoTags(): void {
    this.eventSubscriber = this.eventManager.subscribe('grupoTagListModification', () => this.loadAll());
  }

  delete(grupoTag: IGrupoTag): void {
    const modalRef = this.modalService.open(GrupoTagDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.grupoTag = grupoTag;
  }
}
