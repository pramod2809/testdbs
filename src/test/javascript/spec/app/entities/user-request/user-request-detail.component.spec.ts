import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ManagementsystemTestModule } from '../../../test.module';
import { USER_REQUESTDetailComponent } from 'app/entities/user-request/user-request-detail.component';
import { USER_REQUEST } from 'app/shared/model/user-request.model';

describe('Component Tests', () => {
  describe('USER_REQUEST Management Detail Component', () => {
    let comp: USER_REQUESTDetailComponent;
    let fixture: ComponentFixture<USER_REQUESTDetailComponent>;
    const route = ({ data: of({ uSER_REQUEST: new USER_REQUEST(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ManagementsystemTestModule],
        declarations: [USER_REQUESTDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(USER_REQUESTDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(USER_REQUESTDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load uSER_REQUEST on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.uSER_REQUEST).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
