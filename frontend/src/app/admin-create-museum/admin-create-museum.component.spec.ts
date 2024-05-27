import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCreateMuseumComponent } from './admin-create-museum.component';

describe('AdminCreateMuseumComponent', () => {
  let component: AdminCreateMuseumComponent;
  let fixture: ComponentFixture<AdminCreateMuseumComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminCreateMuseumComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminCreateMuseumComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
