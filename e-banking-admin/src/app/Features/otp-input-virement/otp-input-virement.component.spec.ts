import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OtpInputVirementComponent } from './otp-input-virement.component';

describe('OtpInputComponent', () => {
  let component: OtpInputVirementComponent;
  let fixture: ComponentFixture<OtpInputVirementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OtpInputVirementComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OtpInputVirementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
