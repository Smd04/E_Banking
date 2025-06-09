import { Injectable } from '@angular/core';

import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import {Compte} from '../../models/models-client/Compte';
import { Transaction } from '../../models/models-client/Transaction';



@Injectable({
  providedIn: 'root'
})
export class CompteServiceService {
  private apiUrl = 'http://localhost:8080/project_e_banking_war/api/account/';

  constructor(private http: HttpClient) {}

  getComptesByClient(): Observable<Compte[]> {
    console.log("get compte by client");
    return this.http.get<Compte[]>(`${this.apiUrl}mesComptes`);
  }

  getComptesById(idCompte: number) {
    return this.http.get<Compte>(`${this.apiUrl}${idCompte}`);
  }
  getTransactionByCompteId(id:number):Observable<Transaction[]>{
    return  this.http.get<Transaction[]>(`${this.apiUrl}${id}/transactions`);
  }
  supprimerCompte(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}${id}`);
  }

  activerOuDesactiverCompte(id: number, statut: string): Observable<any> {
    return this.http.put(`${this.apiUrl}${id}/statut`, { statut });
  }
  getCarteByCompteId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}${id}/carte`);
  }
  getRecentTransactionByCompteId(id:number):Observable<Transaction[]>{
    return  this.http.get<Transaction[]>(`http://localhost:8090/project_e_banking_war/api/account/${id}/RecentTransaction`);
  }
   getMonthlyBalance(id:number):Observable<any>{
    return  this.http.get(`${this.apiUrl}${id}/MonthlyBalance`);
   }
}
