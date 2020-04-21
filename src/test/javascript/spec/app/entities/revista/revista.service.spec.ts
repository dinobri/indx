import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RevistaService } from 'app/entities/revista/revista.service';
import { IRevista, Revista } from 'app/shared/model/revista.model';
import { Periodicidade } from 'app/shared/model/enumerations/periodicidade.model';

describe('Service Tests', () => {
  describe('Revista Service', () => {
    let injector: TestBed;
    let service: RevistaService;
    let httpMock: HttpTestingController;
    let elemDefault: IRevista;
    let expectedResult: IRevista | IRevista[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(RevistaService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Revista(0, 'AAAAAAA', 'AAAAAAA', Periodicidade.MENSAL);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Revista', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Revista()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Revista', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            descricao: 'BBBBBB',
            periodicidade: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Revista', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            descricao: 'BBBBBB',
            periodicidade: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Revista', () => {
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
