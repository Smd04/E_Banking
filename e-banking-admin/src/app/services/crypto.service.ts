import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CryptoService {
  private coingeckoUrl = 'https://api.coingecko.com/api/v3/coins/markets';
  private apiBaseUrl = 'http://localhost:8080/project_e_banking_war/api/crypto';
  private allCryptos: any[] = [];

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    if (!token) {
      throw new Error('No authentication token found');
    }
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  private parseErrorResponse(error: any): string {
    try {
      // Try to parse as JSON first
      const errorObj = typeof error === 'string' ? JSON.parse(error) : error;
      return errorObj.message || errorObj.error || JSON.stringify(errorObj);
    } catch (e) {
      // If not JSON, check if it's HTML
      if (typeof error === 'string' && error.startsWith('<!doctype')) {
        return 'Server returned an HTML error page';
      }
      return error.message || error.toString();
    }
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred';

    if (error.error) {
      // Log exact error body and status
      console.error('Error status:', error.status);
      console.error('Error body:', error.error);
    }
    
    if (error.status === 0) {
      errorMessage = 'Network error: Please check your internet connection';
    } else if (error.status === 401) {
      errorMessage = 'Session expired. Please log in again.';
    } else if (error.status === 403) {
      errorMessage = 'Crypto services are not enabled for this user';
    } else if (error.status === 404) {
      errorMessage = 'Requested resource not found';
    } else if (error.status >= 400 && error.status < 500) {
      errorMessage = 'Crypto services are not enabled for this user';
    } else if (error.status >= 500) {
      errorMessage = 'Server error: Please try again later';
    }

    console.error('API Error:', error);
    return throwError(() => new Error(errorMessage));
  }

  getInitialCryptoData(): Observable<any> {
    const params = new HttpParams()
      .set('vs_currency', 'usd')
      .set('order', 'market_cap_desc')
      .set('per_page', '250')
      .set('page', '1')
      .set('sparkline', 'false');
      
    return this.http.get(this.coingeckoUrl, { 
      params,
      responseType: 'json' 
    }).pipe(
      catchError(this.handleError)
    );
  }

  getAllCryptos(userId: number, page: number = 1, perPage: number = 250): Observable<any[]> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('perPage', perPage.toString());
    return this.http.get<any[]>(`${this.apiBaseUrl}/all/${userId}`, { 
      params,
      headers: this.getHeaders(),
      responseType: 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }

  searchCryptos(userId: number, query: string, page: number = 1, perPage: number = 250): Observable<any[]> {
    const params = new HttpParams()
      .set('query', query)
      .set('page', page.toString())
      .set('perPage', perPage.toString());
    return this.http.get<any[]>(`${this.apiBaseUrl}/search/${userId}`, { 
      params,
      headers: this.getHeaders(),
      responseType: 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }

  getCryptoDetails(userId: number, cryptoId: string): Observable<any> {
    return this.http.get<any>(`${this.apiBaseUrl}/details/${userId}/${cryptoId}`, { 
      headers: this.getHeaders(),
      responseType: 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }

  buyCrypto(userId: number, transaction: any): Observable<any> {
    return this.http.post(`${this.apiBaseUrl}/buy/${userId}`, transaction, { 
      headers: this.getHeaders(),
      responseType: 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }

  sellCrypto(userId: number, transaction: any): Observable<any> {
    return this.http.post(`${this.apiBaseUrl}/sell/${userId}`, transaction, { 
      headers: this.getHeaders(),
      responseType: 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }

  getUserTransactions(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiBaseUrl}/transactions/${userId}`, { 
      headers: this.getHeaders(),
      responseType: 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }

  localSearch(query: string): any[] {
    if (!query) return this.allCryptos;
    return this.allCryptos.filter(crypto => 
      crypto.name.toLowerCase().includes(query.toLowerCase()) || 
      crypto.symbol.toLowerCase().includes(query.toLowerCase())
    );
  }

  setAllCryptos(cryptos: any[]): void {
    this.allCryptos = cryptos;
  }

  addMoreCryptos(cryptos: any[]): void {
    this.allCryptos = [...this.allCryptos, ...cryptos];
  }
}