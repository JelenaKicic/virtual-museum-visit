import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { DatePipe } from '@angular/common';
import { Tour } from '../models/tour.model';
import { TourService } from '../services/tour.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-admin-create-tour',
  templateUrl: './admin-create-tour.component.html',
  styleUrls: ['./admin-create-tour.component.css'],
})
export class AdminCreateTourComponent implements OnInit {
  public createTourForm: FormGroup = new FormGroup({});
  private filesFormData: FormData = new FormData();
  public numOfUploadedFiles: number = 0;

  constructor(
    public formBuilder: FormBuilder,
    private datepipe: DatePipe,
    private tourService: TourService,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.createTourForm = this.formBuilder.group({
      startDate: new FormControl('', [Validators.required]),
      startTime: new FormControl('', [Validators.required]),
      length: new FormControl('', [Validators.required]),
      video: new FormControl(''),
      price: new FormControl('', [Validators.required]),
    });
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.filesFormData = new FormData();
      this.numOfUploadedFiles = event.target.files.length;

      for (let i = 0; i < this.numOfUploadedFiles; i++) {
        this.filesFormData.append('files', event.target.files[i]);
      }
    }
  }

  onSubmit({ value, valid }: { value: Tour; valid: boolean }) {
    if (valid && this.filesFormData.has('files')) {
      value.startDate = this.datepipe.transform(value.startDate, 'yyyy-MM-dd');
      let museumId = Number(this.route.snapshot.paramMap.get('museum_id'));
      this.tourService.addTour(value, museumId).subscribe(
        (tour) => {
          if (this.filesFormData.has('files')) {
            this.tourService.uploadFiles(tour.id, this.filesFormData).subscribe(
              () => {
                this.createTourForm.reset();
                this.filesFormData = new FormData();
                this.numOfUploadedFiles = 0;
              },
              (err) => {
                this.snackBar.open(err.error, undefined, {
                  duration: 5000,
                });
              }
            );
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
