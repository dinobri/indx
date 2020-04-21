import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IndxSharedModule } from 'app/shared/shared.module';
import { GrupoTagComponent } from './grupo-tag.component';
import { GrupoTagDetailComponent } from './grupo-tag-detail.component';
import { GrupoTagUpdateComponent } from './grupo-tag-update.component';
import { GrupoTagDeleteDialogComponent } from './grupo-tag-delete-dialog.component';
import { grupoTagRoute } from './grupo-tag.route';

@NgModule({
  imports: [IndxSharedModule, RouterModule.forChild(grupoTagRoute)],
  declarations: [GrupoTagComponent, GrupoTagDetailComponent, GrupoTagUpdateComponent, GrupoTagDeleteDialogComponent],
  entryComponents: [GrupoTagDeleteDialogComponent]
})
export class IndxGrupoTagModule {}
