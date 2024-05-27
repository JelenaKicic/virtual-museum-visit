import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { FormErrorStateMatcher } from '../registration/registration.component';
import { TourService } from '../services/tour.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-buy-tour',
  templateUrl: './buy-tour.component.html',
  styleUrls: ['./buy-tour.component.css'],
})
export class BuyTourComponent implements OnInit {
  public buyTourForm: FormGroup = new FormGroup({});
  public hidePassword = true;
  public cardTypes = ['VISA', 'MASTERCARD', 'AMERICAN EXPRESS'];

  constructor(
    private tourService: TourService,
    public formBuilder: FormBuilder,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    private datepipe: DatePipe,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.buyTourForm = this.formBuilder.group(
      {
        name: new FormControl('', [Validators.required]),
        surname: new FormControl('', [Validators.required]),
        number: new FormControl('', [Validators.required]),
        pin: new FormControl('', [Validators.required]),
        type: new FormControl('', [Validators.required]),
        expirationdate: new FormControl('', [Validators.required]),
      },
      { validators: this.checkExpiryDate }
    );
  }

  matcher = new FormErrorStateMatcher();

  checkExpiryDate: ValidatorFn = (
    group: AbstractControl
  ): ValidationErrors | null => {
    let date = group.get('expirationdate')?.value;
    return date.match(/^(0[1-9]|1[0-2])\/?([0-9]{4}|[0-9]{2})$/)
      ? null
      : { notValid: true };
  };

  onSubmit({ value, valid }: { value: any; valid: boolean }) {
    if (valid) {
      value.startDate = this.datepipe.transform(
        '1/' + value.expirationdate,
        'yyyy-MM-dd'
      );
      let tourId = Number(this.route.snapshot.paramMap.get('tour_id'));
      this.tourService.buyTour(tourId, value).subscribe(
        (user) => {
          let museumId = Number(this.route.snapshot.paramMap.get('museum_id'));
          this.router.navigate(['museums', museumId], {
            state: { museumId: museumId },
          });
        },
        (err) => {
          this.snackBar.open(err.error, undefined, {
            duration: 5000,
          });
        }
      );
    }
  }
}
