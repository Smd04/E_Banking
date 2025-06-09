import { Injectable } from '@angular/core';

import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import {Compte} from '../../models/models-client/Compte';
import {MonthlyBalance} from '../../models/models-client/MonthlyBalance';
import {Transaction} from '../../models/models-client/Transaction';



@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private baseUrl = 'http://localhost:8090/project_e_banking_war_exploded/api/dashboard';

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
  getMonthlyBalance(){
    const token : string | null = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    return this.http.get<MonthlyBalance>(
      `${this.baseUrl}/monthly_balance`,
      {
        headers
      }
    )

  }
  getRecentTransaction(){
    const token=localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    })
    return this.http.get<Transaction[]>(
      `${this.baseUrl}/recent_transactions`,
      {
        headers
      }
    )
  }
  getBalanceMonth(){
    const token=localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    })
    return this.http.get(
      `${this.baseUrl}/activiter_compte`,
      {
        headers
      }
    )

  }
}
