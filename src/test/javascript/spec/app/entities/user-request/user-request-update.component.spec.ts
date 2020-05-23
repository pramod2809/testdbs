import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ManagementsystemTestModule } from '../../../test.module';
import { USER_REQUESTUpdateComponent } from 'app/entities/user-request/user-request-update.component';
import { USER_REQUESTService } from 'app/entities/user-request/user-request.service';
import { USER_REQUEST } from 'app/shared/model/user-request.model';

describe('Component Tests', () => {
  describe('USER_REQUEST Management Update Component', () => {
    let comp: USER_REQUESTUpdateComponent;
    let fixture: ComponentFixture<USER_REQUESTUpdateComponent>;
    let service: USER_REQUESTService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ManagementsystemTestModule],
        declarations: [USER_REQUESTUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(USER_REQUESTUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(USER_REQUESTUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(USER_REQUESTService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new USER_REQUEST(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new USER_REQUEST();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
