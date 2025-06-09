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
  id?: number;         // make sure backend sends this field
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

  constructor(private http: HttpClient, private router: Router) {}

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.baseUrl}/login`, credentials, {
      withCredentials: true
    }).pipe(
      tap(response => {
        localStorage.setItem('token', response.accessToken);
        const decoded = jwtDecode<JwtPayload>(response.accessToken);

        const user: User = {
          id: String(decoded.id),  // ✅ Fix applied here
          email: decoded.email || credentials.username,
          firstName:'',
          lastName: '',
        };


        localStorage.setItem('email', user.email);
        localStorage.setItem('userId', user.id);  // ✅ Save user ID to localStorage
        this.currentUserSubject.next(user);
      })
    );
  }

  token(): string | null {
    return localStorage.getItem('token');
  }

  logout(): void {
    this.currentUserSubject.next(null);
    localStorage.removeItem('email');
    localStorage.removeItem('userId');  // ✅ Clean up
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  getPhoneNumber(): string | null {
    const token = this.token();
    if (!token) return null;

    try {
      const decoded = jwtDecode<JwtPayload>(token);
      return decoded.phone|| '';
    } catch (error) {
      console.error('Erreur de décodage du token :', error);
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
      console.error('Erreur lors du décodage du token:', e);
      return null;
    }
  }

  getUserId(): number | null {
    const id = localStorage.getItem('userId');
    return id ? parseInt(id, 10) : null;
  }
}
