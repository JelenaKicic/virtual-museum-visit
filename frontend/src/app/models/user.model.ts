export class User {
  id: number | null;
  name: string | null;
  surname: string | null;
  username: string | null;
  email: Date | null;
  role: string | null;
  status: string | null;
  token: string | null;

  constructor(
    name?: string,
    surname?: string,
    username?: string,
    email?: Date,
    role?: string,
    status?: string,
    token?: string,
    id?: number
  ) {
    this.id = id || null;
    this.name = name || null;
    this.surname = surname || null;
    this.username = username || null;
    this.email = email || null;
    this.role = role || null;
    this.status = status || null;
    this.token = token || null;
  }
}
