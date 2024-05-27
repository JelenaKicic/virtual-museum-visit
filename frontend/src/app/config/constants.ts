import { Injectable } from '@angular/core';
@Injectable()
export class Constants {
  public static readonly API_URL: string = 'http://127.0.0.1:8090';
  public static readonly ADMIN_CRUD_URL: string =
    'http://127.0.0.1::8080/administratorCrud_war_exploded/adminList.jsp?token=';
}
