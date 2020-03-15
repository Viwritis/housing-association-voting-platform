import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HousingAssociationVotingPlatformTestModule } from '../../../test.module';
import { VotingComponent } from 'app/entities/voting/voting.component';
import { VotingService } from 'app/entities/voting/voting.service';
import { Voting } from 'app/shared/model/voting.model';

describe('Component Tests', () => {
  describe('Voting Management Component', () => {
    let comp: VotingComponent;
    let fixture: ComponentFixture<VotingComponent>;
    let service: VotingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HousingAssociationVotingPlatformTestModule],
        declarations: [VotingComponent]
      })
        .overrideTemplate(VotingComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VotingComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VotingService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Voting(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.votings && comp.votings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
