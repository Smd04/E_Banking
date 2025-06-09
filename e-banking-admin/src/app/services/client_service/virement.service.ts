import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

interface VirementRequest {
  date: Date;
  compteUser: string;
  compteDestinataire: string;
  montant: Number;
  motif: string;
}

interface VirementResponse {
  message: string;
}

@Injectable({
  providedIn: 'root'
})
export class VirementService {
  private baseUrl = 'http://localhost:8090/project_e_banking_war_exploded/api/virement';
  message='';

  constructor(private http: HttpClient, private router: Router) { }
  initierVirement(credentials: VirementRequest): Observable<any> {
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
        withCredentials: true,
        responseType: 'text' as 'json',
      },
    );
  }

  validerOtp( code: string): Observable<any> {
    return this.http.post<any>(
      `${this.baseUrl}/valider`,
      {
        code
      },
      { withCredentials: true,
        responseType: 'text' as 'json',
      }
    );
  }


  effectueVirement(credentials: VirementRequest): Observable<VirementResponse> {
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
      date: credentials.date.toISOString(),
    };

    return this.http.post<VirementResponse>(
      `${this.baseUrl}/virement`,
      payload,
      {
        withCredentials: true,
      }
    ).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Erreur lors du virement:', error);
        if (error.status === 401) {
          localStorage.removeItem('token');
          this.router.navigate(['/login']);
        }
        return throwError(() => error);
      })
    );
  }
}
