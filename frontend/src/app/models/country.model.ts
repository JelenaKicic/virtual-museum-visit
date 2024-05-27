import { CountryName } from './countryName.model';

export class Country {
  cca2: string;
  name: CountryName;

  constructor(cca2: string, name: CountryName) {
    this.cca2 = cca2;
    this.name = name;
  }
}
