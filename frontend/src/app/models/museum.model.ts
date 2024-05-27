export class Museum {
  id: number | null;
  name: string | null;
  type: string | null;
  city: string | null;
  country: string | null;
  address: string | null;
  latitude: number | null;
  longitude: number | null;
  region: string | null;
  phoneNumber: string | null;

  constructor(
    id?: number,
    name?: string,
    type?: string,
    city?: string,
    country?: string,
    address?: string,
    latitude?: number,
    longitude?: number,
    region?: string,
    phoneNumber?: string
  ) {
    this.id = id || null;
    this.name = name || null;
    this.type = type || null;
    this.city = city || null;
    this.country = country || null;
    this.address = address || null;
    this.latitude = latitude || null;
    this.longitude = longitude || null;
    this.region = region || null;
    this.phoneNumber = phoneNumber || null;
  }
}
