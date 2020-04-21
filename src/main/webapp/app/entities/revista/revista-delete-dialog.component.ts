import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRevista } from 'app/shared/model/revista.model';
import { RevistaService } from './revista.service';

@Component({
  templateUrl: './revista-delete-dialog.component.html'
})
export class RevistaDeleteDialogComponent {
  revista?: IRevista;

  constructor(protected revistaService: RevistaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.revistaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('revistaListModification');
      this.activeModal.close();
    });
  }
}
