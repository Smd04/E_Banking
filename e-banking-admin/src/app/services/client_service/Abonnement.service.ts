import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import {Abonnement} from '../../models/models-client/Abonnement';




@Injectable({
  providedIn: 'root'
})
export class AbonnementService {
  private apiUrl = 'http://localhost:8080/project_e_banking_war/api/abonnement';

  constructor(private http: HttpClient) { }

  valideAbonnement(abonnemtDetails: Abonnement): Observable<Abonnement> {
    const token = localStorage.getItem('token');
    console.log(abonnemtDetails);
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`});
    return this.http.post<Abonnement>(
      `${this.apiUrl}/auto`,
      {...abonnemtDetails,
        dateProchainCheck:abonnemtDetails.dateProchainCheck.toISOString(),
      },
      {
        withCredentials: true
      }
    );

  }
}
