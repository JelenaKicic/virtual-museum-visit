import { User } from './user.model';

export class Logs {
  id: number | null;
  time: string | null;
  action: string | null;
  user: User | null;

  constructor(id?: number, time?: string, action?: string, user?: User) {
    this.id = id || null;
    this.time = time || null;
    this.action = action || null;
    this.user = user || null;
  }
}
