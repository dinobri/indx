import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGrupoTag } from 'app/shared/model/grupo-tag.model';
import { GrupoTagService } from './grupo-tag.service';

@Component({
  templateUrl: './grupo-tag-delete-dialog.component.html'
})
export class GrupoTagDeleteDialogComponent {
  grupoTag?: IGrupoTag;

  constructor(protected grupoTagService: GrupoTagService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.grupoTagService.delete(id).subscribe(() => {
      this.eventManager.broadcast('grupoTagListModification');
      this.activeModal.close();
    });
  }
}
