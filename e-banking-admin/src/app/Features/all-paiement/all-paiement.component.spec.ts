import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllPaiementComponent } from './all-paiement.component';

describe('AllPaiementComponent', () => {
  let component: AllPaiementComponent;
  let fixture: ComponentFixture<AllPaiementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AllPaiementComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllPaiementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
