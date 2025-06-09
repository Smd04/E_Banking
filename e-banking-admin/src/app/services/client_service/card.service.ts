import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Card} from '../../models/models-client/Card';



@Injectable({
  providedIn: 'root'
})
export class CardService {
  private baseUrl = 'http://localhost:8080/project_e_banking_war/api/card';

  constructor(private http: HttpClient) {}


  getCard(){
    const token : string | null = localStorage.getItem('token');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    return this.http.get<Card[]>(
      `${this.baseUrl}/all`,
      {
        headers
      }
    )

  }
}
