import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComptesComponentComponent } from './comptes-component.component';

describe('ComptesComponentComponent', () => {
  let component: ComptesComponentComponent;
  let fixture: ComponentFixture<ComptesComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComptesComponentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ComptesComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
