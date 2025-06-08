import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CryptoService {
  private coingeckoUrl = 'https://api.coingecko.com/api/v3/coins/markets';
  private apiBaseUrl = 'http://localhost:8080/project_e_banking_war_exploded/api/crypto';
  private allCryptos: any[] = [];

  constructor(private http: HttpClient) {}

  private handleError(error: HttpErrorResponse) {
    if (error.status === 403) {
      return throwError(() => 'Crypto services are not enabled for this user');
    }
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      return throwError(() => `Error: ${error.error.message}`);
    } else {
      // Server-side error
      return throwError(() => `Error Code: ${error.status}\nMessage: ${error.message}`);
    }
  }

  getInitialCryptoData(): Observable<any> {
    const params = new HttpParams()
      .set('vs_currency', 'usd')
      .set('order', 'market_cap_desc')
      .set('per_page', '250')
      .set('page', '1')
      .set('sparkline', 'false');
    return this.http.get(this.coingeckoUrl, { params }).pipe(
      catchError(this.handleError)
    );
  }

  getAllCryptos(userId: number, page: number = 1, perPage: number = 250): Observable<any[]> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('perPage', perPage.toString());
    return this.http.get<any[]>(`${this.apiBaseUrl}/all/${userId}`, { params }).pipe(
      catchError(this.handleError)
    );
  }

  searchCryptos(userId: number, query: string, page: number = 1, perPage: number = 250): Observable<any[]> {
    const params = new HttpParams()
      .set('query', query)
      .set('page', page.toString())
      .set('perPage', perPage.toString());
    return this.http.get<any[]>(`${this.apiBaseUrl}/search/${userId}`, { params }).pipe(
      catchError(error => throwError(() => new Error(error.error?.message || 'Failed to search cryptocurrencies')))
    );
  }

  getCryptoDetails(userId: number, cryptoId: string): Observable<any> {
    return this.http.get<any>(`${this.apiBaseUrl}/details/${userId}/${cryptoId}`).pipe(
      catchError(error => throwError(() => new Error(error.error?.message || 'Failed to get cryptocurrency details')))
    );
  }

  buyCrypto(userId: number, transaction: any): Observable<any> {
    return this.http.post(`${this.apiBaseUrl}/buy/${userId}`, transaction, { responseType: 'text' }).pipe(
      catchError(error => throwError(() => new Error(error.error || 'Failed to buy cryptocurrency')))
    );
  }

  sellCrypto(userId: number, transaction: any): Observable<any> {
    return this.http.post(`${this.apiBaseUrl}/sell/${userId}`, transaction, { responseType: 'text' }).pipe(
      catchError(error => throwError(() => new Error(error.error || 'Failed to sell cryptocurrency')))
    );
  }

  getUserTransactions(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiBaseUrl}/transactions/${userId}`).pipe(
      catchError(error => throwError(() => new Error(error.error?.message || 'Failed to fetch transactions')))
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
