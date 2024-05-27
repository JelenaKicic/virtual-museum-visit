import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuyTourComponent } from './buy-tour.component';

describe('BuyTourComponent', () => {
  let component: BuyTourComponent;
  let fixture: ComponentFixture<BuyTourComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BuyTourComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BuyTourComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
