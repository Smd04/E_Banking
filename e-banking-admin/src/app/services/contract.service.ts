// src/app/services/contract.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ContractRequest } from '../models/contract-request';
import { Contract } from '../models/contract';

@Injectable({
  providedIn: 'root'
})
export class ContractService {
  private apiUrl = 'http://localhost:8080/project_e_banking_war_exploded/api/banque'; 

  constructor(private http: HttpClient) {}

  createContract(contractRequest: ContractRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/create-contract`, contractRequest);
  }

  getAllContracts(): Observable<Contract[]> {
    return this.http.get<Contract[]>(`${this.apiUrl}/create-contract`);
  }

  getContractById(id: number): Observable<Contract> {
    return this.http.get<Contract>(`${this.apiUrl}/create-contract/${id}`);
  }

  updateContract(id: number, contractRequest: ContractRequest): Observable<any> {
    return this.http.put(`${this.apiUrl}/create-contract/${id}`, contractRequest);
  }

  deleteContract(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/create-contract/${id}`);
  }

  getContractsByUserId(userId: number): Observable<Contract[]> {
    return this.http.get<Contract[]>(`${this.apiUrl}/create-contract/user/${userId}`);
  }

  getContractsByStatus(status: string): Observable<Contract[]> {
    return this.http.get<Contract[]>(`${this.apiUrl}/create-contract/status/${status}`);
  }

  getContractsByType(type: string): Observable<Contract[]> {
    return this.http.get<Contract[]>(`${this.apiUrl}/create-contract/type/${type}`);
  }
}