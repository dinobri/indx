import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IndxSharedModule } from 'app/shared/shared.module';
import { EdicaoComponent } from './edicao.component';
import { EdicaoDetailComponent } from './edicao-detail.component';
import { EdicaoUpdateComponent } from './edicao-update.component';
import { EdicaoDeleteDialogComponent } from './edicao-delete-dialog.component';
import { edicaoRoute } from './edicao.route';

@NgModule({
  imports: [IndxSharedModule, RouterModule.forChild(edicaoRoute)],
  declarations: [EdicaoComponent, EdicaoDetailComponent, EdicaoUpdateComponent, EdicaoDeleteDialogComponent],
  entryComponents: [EdicaoDeleteDialogComponent]
})
export class IndxEdicaoModule {}
