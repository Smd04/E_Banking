import { Component, OnInit, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CryptoService } from '../../services/crypto.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-crypto-list',
  standalone: true,
  imports: [CommonModule, HttpClientModule, FormsModule, RouterLink],
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
  userId = 7;
  showNotification = false;
  notificationMessage = '';
  isSuccess = false;
  currentPage = 1;
  perPage = 250;
  allCryptosLoaded = false;
  defaultImage = 'assets/default-crypto.png';

  constructor(private cryptoService: CryptoService) {}

  ngOnInit(): void {
    this.loadInitialData();
  }

  private showNotificationMessage(message: string, isSuccess: boolean) {
    this.notificationMessage = message;
    this.isSuccess = isSuccess;
    this.showNotification = true;
    setTimeout(() => this.showNotification = false, 15000);
  }

  loadInitialData(): void {
    this.loading = true;
    this.cryptoService.getInitialCryptoData().subscribe({
      next: (data) => {
        this.cryptos = data.map((c: any) => ({ ...c, image: c.image || this.defaultImage }));
        this.filteredCryptos = [...this.cryptos];
        this.cryptoService.setAllCryptos(this.cryptos);
        this.loading = false;
      },
      error: (err) => {
        this.showNotificationMessage(err, false);
        this.loading = false;
      }
    });
  }

  loadMoreCryptos(): void {
    if (this.allCryptosLoaded || this.loadingMore) return;
    this.loadingMore = true;
    this.currentPage++;
    
    this.cryptoService.getAllCryptos(this.userId, this.currentPage, this.perPage).subscribe({
      next: (newCryptos) => {
        if (newCryptos.length === 0) {
          this.allCryptosLoaded = true;
        } else {
          const enhancedCryptos = newCryptos.map((c: any) => ({ ...c, image: c.image || this.defaultImage }));
          this.cryptos = [...this.cryptos, ...enhancedCryptos];
          this.filteredCryptos = [...this.filteredCryptos, ...enhancedCryptos];
        }
        this.loadingMore = false;
      },
      error: (err) => {
        this.showNotificationMessage(err, false);
        this.loadingMore = false;
        this.allCryptosLoaded = true; // Stop trying to load more
      }
    });
  }


  onSearch(): void {
    this.filteredCryptos = this.cryptoService.localSearch(this.searchQuery);
  }

  selectCrypto(crypto: any): void {
    this.selectedCrypto = crypto;
    this.amount = 0;
  }

  buyCrypto(): void {
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
      error: (err) => this.showNotificationMessage('Purchase failed: ' + err.message, false)
    });
  }

  sellCrypto(): void {
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
      error: (err) => this.showNotificationMessage('Sale failed: ' + err.message, false)
    });
  }

  @HostListener('window:scroll', ['$event'])
  onScroll(): void {
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight - 500) {
      this.loadMoreCryptos();
    }
  }
}
