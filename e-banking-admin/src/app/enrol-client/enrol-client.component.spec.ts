import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EnrolClientComponent } from './enrol-client.component';

describe('EnrolClientComponent', () => {
  let component: EnrolClientComponent;
  let fixture: ComponentFixture<EnrolClientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EnrolClientComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(EnrolClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
