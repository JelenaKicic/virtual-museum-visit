export class Image {
  id: number | null;
  image: string | null;

  constructor(image?: string, id?: number) {
    this.id = id || null;
    this.image = image || null;
  }
}
