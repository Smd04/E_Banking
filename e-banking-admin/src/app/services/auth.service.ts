import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from '../models/models-client/user';
import { jwtDecode } from 'jwt-decode';

interface LoginRequest {
  username: string;
  password: string;
}

interface LoginResponse {
  accessToken: string;
}

interface JwtPayload {
  sub: string;
  id?: number;
  email?: string;
  phone?: string;
  token?: string;
  role?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/project_e_banking_war/api/auth';
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {
    this.initializeAuthState();
  }

  private initializeAuthState(): void {
    const token = this.token();
    if (token) {
      try {
        const decoded = jwtDecode<JwtPayload>(token);
        const user: User = {
          id: String(decoded.id),
          email: decoded.email || localStorage.getItem('email') || '',
          firstName: '',
          lastName: ''
        };
        this.currentUserSubject.next(user);
      } catch (error) {
        this.clearAuthState();
      }
    }
  }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.baseUrl}/login`, credentials, {
      withCredentials: true
    }).pipe(
      tap(response => {
        this.handleLoginResponse(response, credentials.username);
      })
    );
  }

  private handleLoginResponse(response: LoginResponse, username: string): void {
    localStorage.setItem('token', response.accessToken);
    const decoded = jwtDecode<JwtPayload>(response.accessToken);

    const user: User = {
      id: String(decoded.id),
      email: decoded.email || username,
      firstName: '',
      lastName: '',
    };

    localStorage.setItem('email', user.email);
    localStorage.setItem('userId', user.id);
    this.currentUserSubject.next(user);
  }

  token(): string | null {
    return localStorage.getItem('token');
  }

  logout(): void {
    this.clearAuthState();
    this.router.navigate(['/login']);
  }

  private clearAuthState(): void {
    this.currentUserSubject.next(null);
    localStorage.removeItem('email');
    localStorage.removeItem('userId');
    localStorage.removeItem('token');
  }

  getPhoneNumber(): string | null {
    const token = this.token();
    if (!token) return null;

    try {
      const decoded = jwtDecode<JwtPayload>(token);
      return decoded.phone || '';
    } catch (error) {
      console.error('Token decoding error:', error);
      return '';
    }
  }

  getRole(): string | null {
    const token = this.token();
    if (!token) return null;

    try {
      const decoded = jwtDecode<JwtPayload>(token);
      return decoded.role || null;
    } catch (e) {
      console.error('Token decoding error:', e);
      return null;
    }
  }

  getUserId(): number | null {
    const id = localStorage.getItem('userId');
    return id ? parseInt(id, 10) : null;
  }

  isAuthenticated(): boolean {
    return !!this.token();
  }
}