import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManagementsystemSharedModule } from 'app/shared/shared.module';
import { USER_REQUESTComponent } from './user-request.component';
import { USER_REQUESTDetailComponent } from './user-request-detail.component';
import { USER_REQUESTUpdateComponent } from './user-request-update.component';
import { USER_REQUESTDeleteDialogComponent } from './user-request-delete-dialog.component';
import { uSER_REQUESTRoute } from './user-request.route';

@NgModule({
  imports: [ManagementsystemSharedModule, RouterModule.forChild(uSER_REQUESTRoute)],
  declarations: [USER_REQUESTComponent, USER_REQUESTDetailComponent, USER_REQUESTUpdateComponent, USER_REQUESTDeleteDialogComponent],
  entryComponents: [USER_REQUESTDeleteDialogComponent]
})
export class ManagementsystemUSER_REQUESTModule {}
