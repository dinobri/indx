import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IndxTestModule } from '../../../test.module';
import { GrupoTagDetailComponent } from 'app/entities/grupo-tag/grupo-tag-detail.component';
import { GrupoTag } from 'app/shared/model/grupo-tag.model';

describe('Component Tests', () => {
  describe('GrupoTag Management Detail Component', () => {
    let comp: GrupoTagDetailComponent;
    let fixture: ComponentFixture<GrupoTagDetailComponent>;
    const route = ({ data: of({ grupoTag: new GrupoTag(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IndxTestModule],
        declarations: [GrupoTagDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GrupoTagDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GrupoTagDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load grupoTag on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.grupoTag).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
