import { Injectable } from '@angular/core';

import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Compte} from '../../models/models-client/Compte';
import {Paiement} from '../../models/models-client/paiement';



@Injectable({
  providedIn: 'root'
})
export class ListPaiementService {
  private baseUrl = 'http://localhost:8090/project_e_banking_war_exploded/api/paiement';

  constructor(private http: HttpClient) {}

  getComptePrincipalByToken() {
    const token : string | null = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    return this.http.get<Compte>(
      `${this.baseUrl}/account_principal`,
      {
        headers
      }
    )
  }
  getRecentPaiement(){
    const token : string | null = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    return this.http.get<Paiement[]>(
      `${this.baseUrl}/recent_paiement`,
      {
        headers
      }
    )

  }
  getAllPaiement(){
    const token=localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    })
    return this.http.get<Paiement[]>(
      `${this.baseUrl}/all_paiement`,
      {
        headers
      }
    )
  }

}
