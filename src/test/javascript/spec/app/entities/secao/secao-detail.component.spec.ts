import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IndxTestModule } from '../../../test.module';
import { SecaoDetailComponent } from 'app/entities/secao/secao-detail.component';
import { Secao } from 'app/shared/model/secao.model';

describe('Component Tests', () => {
  describe('Secao Management Detail Component', () => {
    let comp: SecaoDetailComponent;
    let fixture: ComponentFixture<SecaoDetailComponent>;
    const route = ({ data: of({ secao: new Secao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IndxTestModule],
        declarations: [SecaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SecaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SecaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load secao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.secao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
