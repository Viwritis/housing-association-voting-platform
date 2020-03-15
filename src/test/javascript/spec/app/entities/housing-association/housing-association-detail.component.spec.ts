import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HousingAssociationVotingPlatformTestModule } from '../../../test.module';
import { HousingAssociationDetailComponent } from 'app/entities/housing-association/housing-association-detail.component';
import { HousingAssociation } from 'app/shared/model/housing-association.model';

describe('Component Tests', () => {
  describe('HousingAssociation Management Detail Component', () => {
    let comp: HousingAssociationDetailComponent;
    let fixture: ComponentFixture<HousingAssociationDetailComponent>;
    const route = ({ data: of({ housingAssociation: new HousingAssociation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HousingAssociationVotingPlatformTestModule],
        declarations: [HousingAssociationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(HousingAssociationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HousingAssociationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load housingAssociation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.housingAssociation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
