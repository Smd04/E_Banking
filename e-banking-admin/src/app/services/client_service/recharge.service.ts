import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

interface RechargeRequest {
  compteUser: string;
  operatorName: string;
  phone: string;
  montant: Number;
}

interface RechargeResponse {
  message: string;
}

@Injectable({
  providedIn: 'root'
})
export class RechargeService {
  private baseUrl = 'http://localhost:8090/project_e_banking_war/api/recharge';

  constructor(private http: HttpClient, private router: Router) { }

  effectueRecharge(credentials: RechargeRequest): Observable<RechargeResponse> {
    let token = localStorage.getItem('token');
    console.log(token);
    if (!token) {
      console.error('Aucun token trouvé');
      this.router.navigate(['/login']);
      return throwError(() => new Error('Authentification requise'));
    }
    if (token.split('.').length !== 3) {
      console.error('Format de token invalide');
      localStorage.removeItem('token');
      this.router.navigate(['/login']);
      return throwError(() => new Error('Token malformé'));
    }
    const payload = {
      ...credentials,
    };

    return this.http.post<RechargeResponse>(
      `${this.baseUrl}/recharge`,
      payload,
      {
        withCredentials: true,
      }

    ).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Erreur lors du recharge:', error);
        if (error.status === 401) {
          localStorage.removeItem('token');
          alert(Response);
          this.router.navigate(['/login']);
        }
        return throwError(() => error);
      })
    );
  }
  initierRecharge(credentials: RechargeRequest): Observable<any> {
    let token = localStorage.getItem('token');
    console.log(token);
    if (!token) {
      console.error('Aucun token trouvé');
      this.router.navigate(['/login']);
      return throwError(() => new Error('Authentification requise'));
    }
    if (token.split('.').length !== 3) {
      console.error('Format de token invalide');
      localStorage.removeItem('token');
      this.router.navigate(['/login']);
      return throwError(() => new Error('Token malformé'));
    }
    const payload = {
      ...credentials,
    };
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    return this.http.post<any>(
      `${this.baseUrl}/initier`,
      {
        ...credentials,
      },
      {headers,
        withCredentials: true },
    );
  }

  validerOtp(phoneNumber: string | null, code: string): Observable<RechargeResponse> {
    return this.http.post<any>(
      `${this.baseUrl}/valider`,
      {
        phoneNumber,
        code
      },
      { withCredentials: true }
    );
  }



}
