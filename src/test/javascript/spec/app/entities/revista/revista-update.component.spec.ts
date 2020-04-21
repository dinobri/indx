import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IndxTestModule } from '../../../test.module';
import { RevistaUpdateComponent } from 'app/entities/revista/revista-update.component';
import { RevistaService } from 'app/entities/revista/revista.service';
import { Revista } from 'app/shared/model/revista.model';

describe('Component Tests', () => {
  describe('Revista Management Update Component', () => {
    let comp: RevistaUpdateComponent;
    let fixture: ComponentFixture<RevistaUpdateComponent>;
    let service: RevistaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IndxTestModule],
        declarations: [RevistaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RevistaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RevistaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RevistaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Revista(123);
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
        const entity = new Revista();
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
