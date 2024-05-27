import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCreateTourComponent } from './admin-create-tour.component';

describe('AdminCreateTourComponent', () => {
  let component: AdminCreateTourComponent;
  let fixture: ComponentFixture<AdminCreateTourComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminCreateTourComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminCreateTourComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
