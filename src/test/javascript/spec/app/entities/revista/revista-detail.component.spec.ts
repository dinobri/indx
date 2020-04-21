import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IndxTestModule } from '../../../test.module';
import { RevistaDetailComponent } from 'app/entities/revista/revista-detail.component';
import { Revista } from 'app/shared/model/revista.model';

describe('Component Tests', () => {
  describe('Revista Management Detail Component', () => {
    let comp: RevistaDetailComponent;
    let fixture: ComponentFixture<RevistaDetailComponent>;
    const route = ({ data: of({ revista: new Revista(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IndxTestModule],
        declarations: [RevistaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RevistaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RevistaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load revista on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.revista).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
