import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  FormGroupDirective,
  NgForm,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { User } from '../models/user.model';
import { RegistrationService } from '../services/registration.service';
import { MatSnackBar } from '@angular/material/snack-bar';

export class FormErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(
    control: FormControl | null,
    form: FormGroupDirective | NgForm | null
  ): boolean {
    const isSubmitted = form && form.submitted;
    return !!(
      control &&
      control.invalid &&
      (control.dirty || control.touched || isSubmitted)
    );
  }
}

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
})
export class RegistrationComponent implements OnInit {
  hidePassword = true;
  hideConfirmPassword = true;

  checkPasswords: ValidatorFn = (
    group: AbstractControl
  ): ValidationErrors | null => {
    let pass = group.get('password')?.value;
    let confirmPass = group.get('confirmPassword')?.value;
    return pass === confirmPass ? null : { notSame: true };
  };

  public registrationForm: FormGroup = new FormGroup({});

  matcher = new FormErrorStateMatcher();

  constructor(
    private registrationService: RegistrationService,
    public formBuilder: FormBuilder,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group(
      {
        name: new FormControl('', [Validators.required]),
        surname: new FormControl('', [Validators.required]),
        username: new FormControl('', [
          Validators.required,
          Validators.minLength(12),
        ]),
        password: new FormControl('', [
          Validators.required,
          Validators.minLength(15),
        ]),
        confirmPassword: new FormControl('', [
          Validators.required,
          Validators.minLength(15),
        ]),
        email: new FormControl('', [Validators.required]),
      },
      { validators: this.checkPasswords }
    );
  }

  onSubmit({ value, valid }: { value: User; valid: boolean }) {
    if (this.registrationForm.valid) {
      this.registrationService.register(value).subscribe(
        (user) => {
          this.registrationForm.reset();
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
