import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { ChatbotComponent } from '../../chatbot/chatbot.component';

interface UserResponse {
  id: number;
  username: string;
  email: string;
  name: string;
  phone: string;
  address: string;
}

@Component({
  selector: 'app-account',
  standalone: true,
  imports: [FormsModule, CommonModule, ChatbotComponent],
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
  user: UserResponse | null = null;
  errorMessage: string = '';
  isLoading: boolean = true;

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.getUserInfo();
  }

  getUserInfo(): void {
    const token = this.authService.token() || localStorage.getItem('token');
    const email = localStorage.getItem('email');
    
    if (!token || !email) {
      this.errorMessage = 'Authentication data missing';
      this.isLoading = false;
      return;
    }

    const apiUrl = 'http://localhost:8080/project_e_banking_war/api/abonnement/getuser';
    
    // Send email as raw string in request body
    this.http.post<UserResponse>(apiUrl, email, {
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'text/plain' // Important for raw string
      }
    }).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('API Error:', error);
        this.errorMessage = error.error?.message || 'Failed to fetch user information';
        this.isLoading = false;
        return of(null);
      })
    ).subscribe({
      next: (userData) => {
        if (userData) {
          this.user = userData;
          console.log('User data:', this.user);
        }
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Subscription Error:', err);
        this.isLoading = false;
      }
    });
  }
}