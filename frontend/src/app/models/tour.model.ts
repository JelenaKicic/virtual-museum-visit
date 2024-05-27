import { SafeResourceUrl } from '@angular/platform-browser';
import { Image } from './image.model';

export class Tour {
  id: number | null;
  startDate: string | null;
  startTime: string | null;
  length: number | null;
  video: string | null;
  price: number | null;
  images: Array<Image> | [];
  isYt: boolean = true;
  safeUrl: SafeResourceUrl | undefined;

  constructor(
    startDate?: string,
    startTime?: string,
    length?: number,
    video?: string,
    price?: number,
    images?: Array<Image>,
    id?: number
  ) {
    this.id = id || null;
    this.startDate = startDate || null;
    this.startTime = startTime || null;
    this.length = length || null;
    this.video = video || null;
    this.video = video || null;
    this.price = price || null;
    this.images = images || [];
  }
}
