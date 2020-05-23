import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'user-request',
        loadChildren: () => import('./user-request/user-request.module').then(m => m.ManagementsystemUSER_REQUESTModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class ManagementsystemEntityModule {}
