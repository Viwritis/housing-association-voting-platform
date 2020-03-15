import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HousingAssociationVotingPlatformTestModule } from '../../../test.module';
import { InhabitantDetailComponent } from 'app/entities/inhabitant/inhabitant-detail.component';
import { Inhabitant } from 'app/shared/model/inhabitant.model';

describe('Component Tests', () => {
  describe('Inhabitant Management Detail Component', () => {
    let comp: InhabitantDetailComponent;
    let fixture: ComponentFixture<InhabitantDetailComponent>;
    const route = ({ data: of({ inhabitant: new Inhabitant(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HousingAssociationVotingPlatformTestModule],
        declarations: [InhabitantDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InhabitantDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InhabitantDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load inhabitant on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.inhabitant).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
