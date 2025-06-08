import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CryptoService } from '../../services/crypto.service';
import { RouterLink } from '@angular/router';

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
  userId: number = 7; // Should be set from auth service in real app
  showNotification = false;
  notificationMessage = '';
  isSuccess = false;
  sortOrder: 'latest' | 'earliest' = 'latest'; // Default sort order

  constructor(private cryptoService: CryptoService) {}

  ngOnInit(): void {
    this.loadTransactions();
  }

  loadTransactions(): void {
    this.loading = true;
    this.cryptoService.getUserTransactions(this.userId).subscribe({
      next: (transactions) => {
        this.transactions = transactions;
        this.sortTransactions();
        this.loading = false;
      },
      error: (err) => {
        this.showNotificationMessage('Failed to fetch transactions', false);
        this.loading = false;
      }
    });
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

  private showNotificationMessage(message: string, isSuccess: boolean) {
    this.notificationMessage = message;
    this.isSuccess = isSuccess;
    this.showNotification = true;

    setTimeout(() => {
      this.showNotification = false;
    }, 10000);
  }
}