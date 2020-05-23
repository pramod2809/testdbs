import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { ManagementsystemTestModule } from '../../../test.module';
import { USER_REQUESTComponent } from 'app/entities/user-request/user-request.component';
import { USER_REQUESTService } from 'app/entities/user-request/user-request.service';
import { USER_REQUEST } from 'app/shared/model/user-request.model';

describe('Component Tests', () => {
  describe('USER_REQUEST Management Component', () => {
    let comp: USER_REQUESTComponent;
    let fixture: ComponentFixture<USER_REQUESTComponent>;
    let service: USER_REQUESTService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ManagementsystemTestModule],
        declarations: [USER_REQUESTComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(USER_REQUESTComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(USER_REQUESTComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(USER_REQUESTService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new USER_REQUEST(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.uSER_REQUESTS && comp.uSER_REQUESTS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new USER_REQUEST(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.uSER_REQUESTS && comp.uSER_REQUESTS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
