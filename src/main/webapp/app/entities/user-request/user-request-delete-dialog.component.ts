import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUSER_REQUEST } from 'app/shared/model/user-request.model';
import { USER_REQUESTService } from './user-request.service';

@Component({
  templateUrl: './user-request-delete-dialog.component.html'
})
export class USER_REQUESTDeleteDialogComponent {
  uSER_REQUEST?: IUSER_REQUEST;

  constructor(
    protected uSER_REQUESTService: USER_REQUESTService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.uSER_REQUESTService.delete(id).subscribe(() => {
      this.eventManager.broadcast('uSER_REQUESTListModification');
      this.activeModal.close();
    });
  }
}
