import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import {User} from '../../models/models-client/user';
import {Info} from '../../models/models-client/info';


@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private baseUrl = 'http://localhost:8090/project_e_banking_war/api/account';

  constructor(private http: HttpClient) {
  }

  getUserByToken() {
    const token: string | null = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    return this.http.get<Info>(
      `${this.baseUrl}/get_user`,
      {
        headers
      }
    )
  }
}
