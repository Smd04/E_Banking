// referral.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

interface CommissionResponse {
  commission: number;
}

@Injectable({ providedIn: 'root' })
export class ReferralService {
  private baseUrl = 'http://localhost:8080/project_e_banking_war/api/user/commission';
  
  constructor(private http: HttpClient) {}

  getReferralCommission(userId: number): Observable<CommissionResponse> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });

    return this.http.get<CommissionResponse>(`${this.baseUrl}/${userId}`, { headers }).pipe(
      catchError(error => {
        console.error('API Error:', error);
        if (error.status === 403) {
          return throwError(() => new Error('You are not allowed to refer. Ask your Agent to give you such authority.'));
        } else if (error.status === 404) {
          return throwError(() => new Error('User settings not found'));
        } else {
          return throwError(() => new Error(
            error.error?.message || error.message || 'Failed to load commission'
          ));
        }
      })
    );
  }
}