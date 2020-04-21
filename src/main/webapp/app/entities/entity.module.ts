import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'revista',
        loadChildren: () => import('./revista/revista.module').then(m => m.IndxRevistaModule)
      },
      {
        path: 'secao',
        loadChildren: () => import('./secao/secao.module').then(m => m.IndxSecaoModule)
      },
      {
        path: 'autor',
        loadChildren: () => import('./autor/autor.module').then(m => m.IndxAutorModule)
      },
      {
        path: 'edicao',
        loadChildren: () => import('./edicao/edicao.module').then(m => m.IndxEdicaoModule)
      },
      {
        path: 'materia',
        loadChildren: () => import('./materia/materia.module').then(m => m.IndxMateriaModule)
      },
      {
        path: 'tag',
        loadChildren: () => import('./tag/tag.module').then(m => m.IndxTagModule)
      },
      {
        path: 'grupo-tag',
        loadChildren: () => import('./grupo-tag/grupo-tag.module').then(m => m.IndxGrupoTagModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class IndxEntityModule {}
