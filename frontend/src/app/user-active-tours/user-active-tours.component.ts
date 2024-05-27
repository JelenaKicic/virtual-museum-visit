import { Component, OnInit } from '@angular/core';
import { Constants } from '../config/constants';
import { Tour } from '../models/tour.model';
import { TourService } from '../services/tour.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-user-active-tours',
  templateUrl: './user-active-tours.component.html',
  styleUrls: ['./user-active-tours.component.css'],
})
export class UserActiveToursComponent implements OnInit {
  public tours: Tour[] = [];

  constructor(
    private tourService: TourService,
    private domSanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.tourService.getActiveTours().subscribe((value) => {
      this.tours = value;

      for (let tour of this.tours) {
        if (!tour.video?.startsWith('http')) {
          tour.video = Constants.API_URL + '/image/' + tour.video;
          tour.isYt = false;
        } else {
          tour.isYt = true;
          tour.safeUrl = this.domSanitizer.bypassSecurityTrustResourceUrl(
            tour.video
          );
        }
        for (let image of tour.images) {
          image.image = Constants.API_URL + '/image/' + image.image;
          console.log(image.image);
        }
      }
    });
  }
}
