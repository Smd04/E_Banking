import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import {Facture} from '../../models/models-client/Facture';




@Injectable({
  providedIn: 'root'
})
export class FactureService {
  private apiUrl = 'http://localhost:8080/project_e_banking_war_exploded/api/factures';

  constructor(private http: HttpClient) { }

  valideFacture(factureDetails: Facture): Observable<Facture> {
    const token = localStorage.getItem('token');
    console.log(factureDetails);
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`});
    return this.http.post<Facture>(
      `${this.apiUrl}/auto`,
      {...factureDetails,
        dateProchainCheck:factureDetails.dateProchainCheck.toISOString(),
      },
      {
        withCredentials: true
      }
    );

  }
}
