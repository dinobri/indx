import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IndxSharedModule } from 'app/shared/shared.module';
import { SecaoComponent } from './secao.component';
import { SecaoDetailComponent } from './secao-detail.component';
import { SecaoUpdateComponent } from './secao-update.component';
import { SecaoDeleteDialogComponent } from './secao-delete-dialog.component';
import { secaoRoute } from './secao.route';

@NgModule({
  imports: [IndxSharedModule, RouterModule.forChild(secaoRoute)],
  declarations: [SecaoComponent, SecaoDetailComponent, SecaoUpdateComponent, SecaoDeleteDialogComponent],
  entryComponents: [SecaoDeleteDialogComponent]
})
export class IndxSecaoModule {}
