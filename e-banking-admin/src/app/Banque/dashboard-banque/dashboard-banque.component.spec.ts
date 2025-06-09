import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardBanqueComponent } from './dashboard-banque.component';

describe('DashboardBanqueComponent', () => {
  let component: DashboardBanqueComponent;
  let fixture: ComponentFixture<DashboardBanqueComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardBanqueComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardBanqueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
