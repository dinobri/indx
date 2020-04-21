import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IndxSharedModule } from 'app/shared/shared.module';
import { RevistaComponent } from './revista.component';
import { RevistaDetailComponent } from './revista-detail.component';
import { RevistaUpdateComponent } from './revista-update.component';
import { RevistaDeleteDialogComponent } from './revista-delete-dialog.component';
import { revistaRoute } from './revista.route';

@NgModule({
  imports: [IndxSharedModule, RouterModule.forChild(revistaRoute)],
  declarations: [RevistaComponent, RevistaDetailComponent, RevistaUpdateComponent, RevistaDeleteDialogComponent],
  entryComponents: [RevistaDeleteDialogComponent]
})
export class IndxRevistaModule {}
