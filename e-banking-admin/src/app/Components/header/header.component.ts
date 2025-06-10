import { Component, HostListener, OnDestroy } from '@angular/core';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  standalone: true,
  imports: [NgbDropdownModule, RouterModule, CommonModule],
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnDestroy {
  isCollapsed = true;
  isLoggedIn = false;
  private authSubscription: Subscription;

  constructor(
    private router: Router,
    private authService: AuthService
  ) {
    this.authSubscription = this.authService.currentUser$.subscribe(user => {
      this.isLoggedIn = !!user;
    });
    // Immediate check in case subscription hasn't fired yet
    this.isLoggedIn = this.authService.isAuthenticated();
  }

  ngOnDestroy(): void {
    this.authSubscription.unsubscribe();
  }

  toggleMenu(): void {
    this.isCollapsed = !this.isCollapsed;
  }

  closeMenu(): void {
    if (window.innerWidth < 992) {
      this.isCollapsed = true;
    }
  }

  getMaxHeight(): number | null {
    if (this.isCollapsed) return null;
    return window.innerHeight - 56; // 56px = header height
  }

  @HostListener('window:resize', ['$event'])
  onResize(): void {
    if (window.innerWidth >= 992) {
      this.isCollapsed = false;
    } else if (!this.isCollapsed) {
      this.isCollapsed = true;
      setTimeout(() => this.isCollapsed = false, 10);
    }
  }

  @HostListener('document:click', ['$event'])
  onClick(event: MouseEvent): void {
    const target = event.target as HTMLElement;
    if (!target.closest('.navbar') && !this.isCollapsed) {
      this.isCollapsed = true;
    }
  }

  // Navigation methods with auth check
  private navigateWithAuthCheck(route: string): void {
    if (!this.isLoggedIn) {
      this.router.navigate(['/login']);
      return;
    }
    this.router.navigate([route]);
    this.closeMenu();
  }

  redirectToCompte(): void {
    this.navigateWithAuthCheck('/comptes');
  }

  redirectToPaimentQRCode(): void {
    this.navigateWithAuthCheck('/paiment-qr-code');
  }

  redirectToVirment(): void {
    this.navigateWithAuthCheck('/virement');
  }

  redirectToRecharge(): void {
    this.navigateWithAuthCheck('/recharge');
  }

  redirectToFacteur(): void {
    this.navigateWithAuthCheck('/facture');
  }

  redirectToAbonnment(): void {
    this.navigateWithAuthCheck('/abonnement');
  }

  redirectToPaiement(): void {
    this.navigateWithAuthCheck('/historique-paiement');
  }

  redirectToHelp(): void {
    this.navigateWithAuthCheck('/support');
  }

  getProfile(): void {
    this.navigateWithAuthCheck('/profile');
  }

  redirectToDashboard(): void {
    this.navigateWithAuthCheck('/dashboard-user');
  }
}