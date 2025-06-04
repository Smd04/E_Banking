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
  private apiUrl = 'http://your-api-url/api'; // Replace with your actual API URL

  constructor(private http: HttpClient) {}

  createContract(contractRequest: ContractRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/contracts`, contractRequest);
  }

  getAllContracts(): Observable<Contract[]> {
    return this.http.get<Contract[]>(`${this.apiUrl}/contracts`);
  }

  getContractById(id: number): Observable<Contract> {
    return this.http.get<Contract>(`${this.apiUrl}/contracts/${id}`);
  }

  updateContract(id: number, contractRequest: ContractRequest): Observable<any> {
    return this.http.put(`${this.apiUrl}/contracts/${id}`, contractRequest);
  }

  deleteContract(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/contracts/${id}`);
  }

  getContractsByUserId(userId: number): Observable<Contract[]> {
    return this.http.get<Contract[]>(`${this.apiUrl}/contracts/user/${userId}`);
  }

  getContractsByStatus(status: string): Observable<Contract[]> {
    return this.http.get<Contract[]>(`${this.apiUrl}/contracts/status/${status}`);
  }

  getContractsByType(type: string): Observable<Contract[]> {
    return this.http.get<Contract[]>(`${this.apiUrl}/contracts/type/${type}`);
  }
}
