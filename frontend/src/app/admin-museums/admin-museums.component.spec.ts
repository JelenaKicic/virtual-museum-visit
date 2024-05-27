import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminMuseumsComponent } from './admin-museums.component';

describe('AdminMuseumsComponent', () => {
  let component: AdminMuseumsComponent;
  let fixture: ComponentFixture<AdminMuseumsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminMuseumsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminMuseumsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
