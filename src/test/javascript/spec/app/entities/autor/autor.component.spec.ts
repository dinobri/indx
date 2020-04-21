import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IndxTestModule } from '../../../test.module';
import { AutorComponent } from 'app/entities/autor/autor.component';
import { AutorService } from 'app/entities/autor/autor.service';
import { Autor } from 'app/shared/model/autor.model';

describe('Component Tests', () => {
  describe('Autor Management Component', () => {
    let comp: AutorComponent;
    let fixture: ComponentFixture<AutorComponent>;
    let service: AutorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IndxTestModule],
        declarations: [AutorComponent]
      })
        .overrideTemplate(AutorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AutorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AutorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Autor(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.autors && comp.autors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
