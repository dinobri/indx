import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IndxTestModule } from '../../../test.module';
import { EdicaoComponent } from 'app/entities/edicao/edicao.component';
import { EdicaoService } from 'app/entities/edicao/edicao.service';
import { Edicao } from 'app/shared/model/edicao.model';

describe('Component Tests', () => {
  describe('Edicao Management Component', () => {
    let comp: EdicaoComponent;
    let fixture: ComponentFixture<EdicaoComponent>;
    let service: EdicaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IndxTestModule],
        declarations: [EdicaoComponent]
      })
        .overrideTemplate(EdicaoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EdicaoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EdicaoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Edicao(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.edicaos && comp.edicaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
