import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaimaentQrCodeComponent } from './paimaent-qr-code.component';

describe('PaimaentQrCodeComponent', () => {
  let component: PaimaentQrCodeComponent;
  let fixture: ComponentFixture<PaimaentQrCodeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaimaentQrCodeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaimaentQrCodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
