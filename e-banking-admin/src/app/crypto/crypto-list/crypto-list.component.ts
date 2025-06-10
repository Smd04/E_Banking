import { Component, OnInit, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CryptoService } from '../../services/crypto.service';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import {HeaderComponent} from '../../Components/header/header.component';

@Component({
  selector: 'app-crypto-list',
  standalone: true,
  imports: [CommonModule, HttpClientModule, FormsModule, RouterLink, HeaderComponent],
  templateUrl: './crypto-list.component.html',
  styleUrls: ['./crypto-list.component.scss']
})
export class CryptoListComponent implements OnInit {
  cryptos: any[] = [];
  filteredCryptos: any[] = [];
  loading = true;
  loadingMore = false;
  searchQuery = '';
  selectedCrypto: any;
  amount = 0;
  userId: number | null = null;
  showNotification = false;
  notificationMessage = '';
  isSuccess = false;
  currentPage = 1;
  perPage = 250;
  allCryptosLoaded = false;
  defaultImage = 'assets/default-crypto.png';
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

    // Load initial data and handle crypto service status
    this.loadInitialData();
  }

  private showNotificationMessage(message: string, isSuccess: boolean) {
    this.notificationMessage = message;
    this.isSuccess = isSuccess;
    this.showNotification = true;
    setTimeout(() => this.showNotification = false, 15000);
  }

  private validateResponse(data: any): any {
    if (typeof data === 'string' && data.startsWith('<!doctype')) {
      throw new Error('Server returned an HTML error page');
    }
    return data;
  }

  loadInitialData(): void {
    this.loading = true;
    this.cryptoService.getInitialCryptoData().subscribe({
      next: (data) => {
        try {
          const validatedData = this.validateResponse(data);
          this.cryptos = validatedData.map((c: any) => ({ 
            ...c, 
            image: c.image || this.defaultImage 
          }));
          this.filteredCryptos = [...this.cryptos];
          this.cryptoService.setAllCryptos(this.cryptos);
          this.loading = false;
        } catch (error) {
          this.handleDataError(error);
        }
      },
      error: (err: any) => this.handleApiError(err)
    });
  }

  loadMoreCryptos(): void {
    if (!this.userId || !this.cryptoEnabled || this.allCryptosLoaded || this.loadingMore) return;
    
    this.loadingMore = true;
    this.currentPage++;
    
    this.cryptoService.getAllCryptos(this.userId, this.currentPage, this.perPage).subscribe({
      next: (newCryptos) => {
        try {
          const validatedData = this.validateResponse(newCryptos);
          if (validatedData.length === 0) {
            this.allCryptosLoaded = true;
          } else {
            const enhancedCryptos = validatedData.map((c: any) => ({ 
              ...c, 
              image: c.image || this.defaultImage 
            }));
            this.cryptos = [...this.cryptos, ...enhancedCryptos];
            this.filteredCryptos = [...this.filteredCryptos, ...enhancedCryptos];
          }
          this.loadingMore = false;
        } catch (error) {
          this.handleDataError(error);
        }
      },
      error: (err: any) => this.handleApiError(err)
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
    }

    this.showNotificationMessage(errorMessage, false);
    this.loading = false;
    this.loadingMore = false;
  }

  private handleDataError(error: any): void {
    console.error('Data Error:', error);
    this.showNotificationMessage('Error processing server response', false);
    this.loading = false;
    this.loadingMore = false;
  }

  onSearch(): void {
    this.filteredCryptos = this.cryptoService.localSearch(this.searchQuery);
  }

  selectCrypto(crypto: any): void {
    if (!this.cryptoEnabled) {
      this.showNotificationMessage('Crypto services are not enabled for your account ask you agent for permission', false);
      return;
    }
    this.selectedCrypto = crypto;
    this.amount = 0;
  }

  buyCrypto(): void {
    if (!this.cryptoEnabled) {
      this.showNotificationMessage('Crypto services are not enabled for your account ask you agent for permission', false);
      return;
    }
    if (!this.userId) {
      this.showNotificationMessage('User not authenticated', false);
      return;
    }
    if (!this.selectedCrypto) {
      this.showNotificationMessage('Please select a cryptocurrency first', false);
      return;
    }
    if (this.amount <= 0) {
      this.showNotificationMessage('Please enter a valid amount', false);
      return;
    }

    const transaction = {
      cryptoId: this.selectedCrypto.id,
      cryptoSymbol: this.selectedCrypto.symbol,
      cryptoName: this.selectedCrypto.name,
      amount: this.amount,
      priceAtTransaction: this.selectedCrypto.current_price
    };

    this.cryptoService.buyCrypto(this.userId, transaction).subscribe({
      next: () => this.showNotificationMessage('Purchase successful!', true),
      error: (err: any) => this.handleApiError(err)
    });
  }

  sellCrypto(): void {
    if (!this.cryptoEnabled) {
      this.showNotificationMessage('Crypto services are not enabled for your account ask you agent for permission', false);
      return;
    }
    if (!this.userId) {
      this.showNotificationMessage('User not authenticated', false);
      return;
    }
    if (!this.selectedCrypto) {
      this.showNotificationMessage('Please select a cryptocurrency first', false);
      return;
    }
    if (this.amount <= 0) {
      this.showNotificationMessage('Please enter a valid amount', false);
      return;
    }

    const transaction = {
      cryptoId: this.selectedCrypto.id,
      cryptoSymbol: this.selectedCrypto.symbol,
      cryptoName: this.selectedCrypto.name,
      amount: this.amount,
      priceAtTransaction: this.selectedCrypto.current_price
    };

    this.cryptoService.sellCrypto(this.userId, transaction).subscribe({
      next: () => this.showNotificationMessage('Sale successful!', true),
      error: (err: any) => this.handleApiError(err)
    });
  }

  @HostListener('window:scroll', ['$event'])
  onScroll(): void {
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight - 500) {
      this.loadMoreCryptos();
    }
  }
}