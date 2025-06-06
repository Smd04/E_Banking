import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OtpInputRechargeComponent } from './otp-input-recharge.component';

describe('OtpInputComponent', () => {
  let component: OtpInputRechargeComponent;
  let fixture: ComponentFixture<OtpInputRechargeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OtpInputRechargeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OtpInputRechargeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
