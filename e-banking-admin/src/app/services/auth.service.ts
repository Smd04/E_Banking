import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable, tap} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {User} from '../models/models-client/user';
import {jwtDecode} from 'jwt-decode';



interface LoginRequest {
  username: string;
  password: string;
}

interface LoginResponse {
  accessToken: string;
}
interface JwtPayload {
  sub: string;
  phoneNumber ?: string;
  token ?: string;
}
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8090/project_e_banking_war/api/auth';
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();
  constructor(private http: HttpClient,private router: Router) { }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.baseUrl}/login`, credentials, {
      withCredentials: true
    }).pipe(
      tap(user => {
        localStorage.setItem('username', 'currentUser');
        localStorage.setItem('token', user.accessToken);

      })
    );
  }


  token(){
    return localStorage.getItem('token');
  }

  logout(): void {
    this.currentUserSubject.next(null);
    localStorage.removeItem('currentUser');
  }
  getPhoneNumber(): string | null {
    const token = this.token();
    if (!token) return null;

    try {
      const decoded = jwtDecode<any>(token);
      return decoded.phoneNumber || null;
    } catch (error) {
      console.error('Erreur de décodage du token :', error);
      return null;
    }
  }
  getRole():string | null {
    const token = this.token();
    if (!token) return null;

    try {
      const decoded = jwtDecode<any>(token);
      return decoded.token || null;
    } catch (error) {
      console.error('Erreur de décodage du token :', error);
      return null;
    }
  }
  }

