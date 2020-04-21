import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISecao } from 'app/shared/model/secao.model';
import { SecaoService } from './secao.service';

@Component({
  templateUrl: './secao-delete-dialog.component.html'
})
export class SecaoDeleteDialogComponent {
  secao?: ISecao;

  constructor(protected secaoService: SecaoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.secaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('secaoListModification');
      this.activeModal.close();
    });
  }
}
