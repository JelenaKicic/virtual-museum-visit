import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { User } from '../models/user.model';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  hide = true;

  public loginForm: FormGroup = new FormGroup({});

  constructor(
    private loginService: LoginService,
    public formBuilder: FormBuilder,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
    });
  }

  onSubmit({ value, valid }: { value: User; valid: boolean }) {
    if (this.loginForm.valid) {
      this.loginService.login(value).subscribe(
        (user) => {
          localStorage.setItem('user', JSON.stringify(user));
          if (user.role === 'ADMIN') {
            this.router.navigate(['/admin_dashboard']);
          } else {
            this.router.navigate(['/user_dashboard']);
          }
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
