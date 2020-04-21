import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IndxTestModule } from '../../../test.module';
import { RevistaComponent } from 'app/entities/revista/revista.component';
import { RevistaService } from 'app/entities/revista/revista.service';
import { Revista } from 'app/shared/model/revista.model';

describe('Component Tests', () => {
  describe('Revista Management Component', () => {
    let comp: RevistaComponent;
    let fixture: ComponentFixture<RevistaComponent>;
    let service: RevistaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IndxTestModule],
        declarations: [RevistaComponent]
      })
        .overrideTemplate(RevistaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RevistaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RevistaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Revista(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.revistas && comp.revistas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
