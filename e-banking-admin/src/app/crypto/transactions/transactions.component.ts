import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CryptoService } from '../../services/crypto.service';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';


@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css']
})
export class TransactionsComponent implements OnInit {
  transactions: any[] = [];
  sortedTransactions: any[] = [];
  loading = true;
  userId: number | null = null;
  showNotification = false;
  notificationMessage = '';
  isSuccess = false;
  sortOrder: 'latest' | 'earliest' = 'latest';
  cryptoEnabled = true;

  constructor(
    private cryptoService: CryptoService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.userId = this.authService.getUserId();
    if (!this.userId) {
      this.showNotificationMessage('User not authenticated', false);
      this.loading = false;
      return;
    }
    this.loadTransactions();
  }

  private showNotificationMessage(message: string, isSuccess: boolean) {
    this.notificationMessage = message;
    this.isSuccess = isSuccess;
    this.showNotification = true;
    setTimeout(() => {
      this.showNotification = false;
    }, 10000);
  }

  private validateResponse(data: any): any {
    if (typeof data === 'string' && data.startsWith('<!doctype')) {
      throw new Error('Server returned an HTML error page');
    }
    return data;
  }

  loadTransactions(): void {
    this.loading = true;
    this.cryptoService.getUserTransactions(this.userId!).subscribe({
      next: (transactions) => {
        try {
          const validatedData = this.validateResponse(transactions);
          this.transactions = validatedData;
          this.sortTransactions();
          this.loading = false;
        } catch (error) {
          this.handleDataError(error);
        }
      },
      error: (err) => this.handleApiError(err)
    });
  }

  private handleApiError(err: any): void {
    console.error('API Error:', err);
    
    let errorMessage = err.message;
    if (err.message.includes('not enabled')) {
      this.cryptoEnabled = false;
      errorMessage = 'Crypto services are not enabled for your account ask you agent for permission';
    } else if (err.message.includes('HTML')) {
      errorMessage = 'Server error: Invalid response format';
    } else if (err.message.includes('Network')) {
      errorMessage = 'Network error: Please check your connection';
    } else if (err.message.includes('401')) {
      errorMessage = 'Session expired. Please log in again.';
    }

    this.showNotificationMessage(`${errorMessage}`, false);
    this.loading = false;
  }

  private handleDataError(error: any): void {
    console.error('Data Error:', error);
    this.showNotificationMessage('Error processing transaction data', false);
    this.loading = false;
  }

  toggleSortOrder(): void {
    this.sortOrder = this.sortOrder === 'latest' ? 'earliest' : 'latest';
    this.sortTransactions();
  }

  private sortTransactions(): void {
    this.sortedTransactions = [...this.transactions].sort((a, b) => {
      const dateA = new Date(a.transactionDate).getTime();
      const dateB = new Date(b.transactionDate).getTime();
      return this.sortOrder === 'latest' ? dateB - dateA : dateA - dateB;
    });
  }
}