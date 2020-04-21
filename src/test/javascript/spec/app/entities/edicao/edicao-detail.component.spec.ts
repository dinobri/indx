import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IndxTestModule } from '../../../test.module';
import { EdicaoDetailComponent } from 'app/entities/edicao/edicao-detail.component';
import { Edicao } from 'app/shared/model/edicao.model';

describe('Component Tests', () => {
  describe('Edicao Management Detail Component', () => {
    let comp: EdicaoDetailComponent;
    let fixture: ComponentFixture<EdicaoDetailComponent>;
    const route = ({ data: of({ edicao: new Edicao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IndxTestModule],
        declarations: [EdicaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EdicaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EdicaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load edicao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.edicao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
