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
  loading = true;
  userId: number = 7; // Should be set from auth service in real app
  showNotification = false;
  notificationMessage = '';
  isSuccess = false;

  constructor(private cryptoService: CryptoService) {}

  ngOnInit(): void {
    this.loadTransactions();
  }

  loadTransactions(): void {
    this.loading = true;
    this.cryptoService.getUserTransactions(this.userId).subscribe({
      next: (transactions) => {
        this.transactions = transactions;
        this.loading = false;
      },
      error: (err) => {
        this.showNotificationMessage('Failed to fetch transactions', false);
        this.loading = false;
      }
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