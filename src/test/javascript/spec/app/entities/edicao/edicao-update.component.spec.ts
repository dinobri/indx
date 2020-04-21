import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IndxTestModule } from '../../../test.module';
import { EdicaoUpdateComponent } from 'app/entities/edicao/edicao-update.component';
import { EdicaoService } from 'app/entities/edicao/edicao.service';
import { Edicao } from 'app/shared/model/edicao.model';

describe('Component Tests', () => {
  describe('Edicao Management Update Component', () => {
    let comp: EdicaoUpdateComponent;
    let fixture: ComponentFixture<EdicaoUpdateComponent>;
    let service: EdicaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IndxTestModule],
        declarations: [EdicaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EdicaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EdicaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EdicaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Edicao(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Edicao();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
