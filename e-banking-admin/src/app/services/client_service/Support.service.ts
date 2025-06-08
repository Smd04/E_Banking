
import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import {Facture} from '../../models/models-client/Facture';
import {SupportRequest} from '../../models/models-client/SupportRequest';




@Injectable({
  providedIn: 'root'
})
export class SupportService {
  private apiUrl = 'http://localhost:8090/project_e_banking_war/api/support';

  constructor(private http: HttpClient) { }

  sendMessage(credentials:SupportRequest):Observable<SupportRequest>{
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`});
    console.log(credentials);
    return this.http.post<SupportRequest>(
      `${this.apiUrl}/send`,

      credentials
      ,
      {
        withCredentials: true
      }
    );

  }
}
