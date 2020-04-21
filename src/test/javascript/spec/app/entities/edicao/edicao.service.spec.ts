import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EdicaoService } from 'app/entities/edicao/edicao.service';
import { IEdicao, Edicao } from 'app/shared/model/edicao.model';

describe('Service Tests', () => {
  describe('Edicao Service', () => {
    let injector: TestBed;
    let service: EdicaoService;
    let httpMock: HttpTestingController;
    let elemDefault: IEdicao;
    let expectedResult: IEdicao | IEdicao[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EdicaoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Edicao(0, 0, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataPublicacao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Edicao', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataPublicacao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataPublicacao: currentDate
          },
          returnedFromService
        );

        service.create(new Edicao()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Edicao', () => {
        const returnedFromService = Object.assign(
          {
            numero: 1,
            dataPublicacao: currentDate.format(DATE_FORMAT),
            referencia: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataPublicacao: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Edicao', () => {
        const returnedFromService = Object.assign(
          {
            numero: 1,
            dataPublicacao: currentDate.format(DATE_FORMAT),
            referencia: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataPublicacao: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Edicao', () => {
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
