import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ManagementsystemTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { USER_REQUESTDeleteDialogComponent } from 'app/entities/user-request/user-request-delete-dialog.component';
import { USER_REQUESTService } from 'app/entities/user-request/user-request.service';

describe('Component Tests', () => {
  describe('USER_REQUEST Management Delete Component', () => {
    let comp: USER_REQUESTDeleteDialogComponent;
    let fixture: ComponentFixture<USER_REQUESTDeleteDialogComponent>;
    let service: USER_REQUESTService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ManagementsystemTestModule],
        declarations: [USER_REQUESTDeleteDialogComponent]
      })
        .overrideTemplate(USER_REQUESTDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(USER_REQUESTDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(USER_REQUESTService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
