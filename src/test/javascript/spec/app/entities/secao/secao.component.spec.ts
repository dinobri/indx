import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IndxTestModule } from '../../../test.module';
import { SecaoComponent } from 'app/entities/secao/secao.component';
import { SecaoService } from 'app/entities/secao/secao.service';
import { Secao } from 'app/shared/model/secao.model';

describe('Component Tests', () => {
  describe('Secao Management Component', () => {
    let comp: SecaoComponent;
    let fixture: ComponentFixture<SecaoComponent>;
    let service: SecaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IndxTestModule],
        declarations: [SecaoComponent]
      })
        .overrideTemplate(SecaoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SecaoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SecaoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Secao(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.secaos && comp.secaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
