import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserActiveToursComponent } from './user-active-tours.component';

describe('UserActiveToursComponent', () => {
  let component: UserActiveToursComponent;
  let fixture: ComponentFixture<UserActiveToursComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserActiveToursComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserActiveToursComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
