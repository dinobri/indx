import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IndxTestModule } from '../../../test.module';
import { GrupoTagUpdateComponent } from 'app/entities/grupo-tag/grupo-tag-update.component';
import { GrupoTagService } from 'app/entities/grupo-tag/grupo-tag.service';
import { GrupoTag } from 'app/shared/model/grupo-tag.model';

describe('Component Tests', () => {
  describe('GrupoTag Management Update Component', () => {
    let comp: GrupoTagUpdateComponent;
    let fixture: ComponentFixture<GrupoTagUpdateComponent>;
    let service: GrupoTagService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IndxTestModule],
        declarations: [GrupoTagUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GrupoTagUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GrupoTagUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GrupoTagService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GrupoTag(123);
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
        const entity = new GrupoTag();
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
