import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Transaction} from '../../models/models-client/Transaction';
import {Compte} from '../../models/models-client/Compte';


@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private apiUrl = 'http://localhost:8080/project_e_banking_war_exploded/api/transaction';
  constructor(private http: HttpClient) {}


  getTransactionsByUser() {
    return this.http.get<Transaction[]>(`${this.apiUrl}/me`);
  }

  getTransactionByAccount(idAccount: number) {
    return this.http.get<Transaction[]>(`${this.apiUrl}/by-compte/${idAccount}`);
  }
  getTransactionById(id: number){
    return this.http.get<Compte[]>(`${this.apiUrl}`);
  }
  getRecentTrasactionOfCompte(idAccount: number){
    return this.http.get<Transaction[]>(`${this.apiUrl}/recentTransaction/${idAccount}`);
  }
}
