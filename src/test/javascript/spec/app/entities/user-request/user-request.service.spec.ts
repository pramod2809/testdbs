import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { USER_REQUESTService } from 'app/entities/user-request/user-request.service';
import { IUSER_REQUEST, USER_REQUEST } from 'app/shared/model/user-request.model';

describe('Service Tests', () => {
  describe('USER_REQUEST Service', () => {
    let injector: TestBed;
    let service: USER_REQUESTService;
    let httpMock: HttpTestingController;
    let elemDefault: IUSER_REQUEST;
    let expectedResult: IUSER_REQUEST | IUSER_REQUEST[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(USER_REQUESTService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new USER_REQUEST(0, 0, 'AAAAAAA', currentDate, 'AAAAAAA', currentDate, 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            submitted_date: currentDate.format(DATE_FORMAT),
            approval_date: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a USER_REQUEST', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            submitted_date: currentDate.format(DATE_FORMAT),
            approval_date: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            submitted_date: currentDate,
            approval_date: currentDate
          },
          returnedFromService
        );

        service.create(new USER_REQUEST()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a USER_REQUEST', () => {
        const returnedFromService = Object.assign(
          {
            request_id: 1,
            submitted_by: 'BBBBBB',
            submitted_date: currentDate.format(DATE_FORMAT),
            approved_by: 'BBBBBB',
            approval_date: currentDate.format(DATE_FORMAT),
            status: 'BBBBBB',
            account_numer: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            submitted_date: currentDate,
            approval_date: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of USER_REQUEST', () => {
        const returnedFromService = Object.assign(
          {
            request_id: 1,
            submitted_by: 'BBBBBB',
            submitted_date: currentDate.format(DATE_FORMAT),
            approved_by: 'BBBBBB',
            approval_date: currentDate.format(DATE_FORMAT),
            status: 'BBBBBB',
            account_numer: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            submitted_date: currentDate,
            approval_date: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a USER_REQUEST', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
