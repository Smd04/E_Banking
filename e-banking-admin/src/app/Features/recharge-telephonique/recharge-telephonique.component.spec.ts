import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RechargeTelephoniqueComponent } from './recharge-telephonique.component';

describe('RechargeTelephoniqueComponent', () => {
  let component: RechargeTelephoniqueComponent;
  let fixture: ComponentFixture<RechargeTelephoniqueComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RechargeTelephoniqueComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RechargeTelephoniqueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
