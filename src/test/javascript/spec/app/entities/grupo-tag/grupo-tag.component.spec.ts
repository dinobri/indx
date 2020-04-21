import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IndxTestModule } from '../../../test.module';
import { GrupoTagComponent } from 'app/entities/grupo-tag/grupo-tag.component';
import { GrupoTagService } from 'app/entities/grupo-tag/grupo-tag.service';
import { GrupoTag } from 'app/shared/model/grupo-tag.model';

describe('Component Tests', () => {
  describe('GrupoTag Management Component', () => {
    let comp: GrupoTagComponent;
    let fixture: ComponentFixture<GrupoTagComponent>;
    let service: GrupoTagService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IndxTestModule],
        declarations: [GrupoTagComponent]
      })
        .overrideTemplate(GrupoTagComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GrupoTagComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GrupoTagService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GrupoTag(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.grupoTags && comp.grupoTags[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
