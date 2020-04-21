import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEdicao } from 'app/shared/model/edicao.model';
import { EdicaoService } from './edicao.service';

@Component({
  templateUrl: './edicao-delete-dialog.component.html'
})
export class EdicaoDeleteDialogComponent {
  edicao?: IEdicao;

  constructor(protected edicaoService: EdicaoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.edicaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('edicaoListModification');
      this.activeModal.close();
    });
  }
}
