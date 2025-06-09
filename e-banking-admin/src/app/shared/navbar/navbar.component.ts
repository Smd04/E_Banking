import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/models-client/user';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  isMenuOpen = false;
  isLoggedIn = false;
  fullname = '';
  role: string | null = null; // Add role as a class property

  constructor(private authService: AuthService, private router: Router) {
    this.authService.currentUser$.subscribe((user: User | null) => {
      this.isLoggedIn = !!user;
      if (user) {
        this.fullname = user.firstName + ' ' + user.lastName || '';
        this.role = localStorage.getItem('role'); // Store role in component property
      } else {
        this.role = null;
      }
    });
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
    this.isMenuOpen = false;
  }

  navigateBasedOnRole(event: Event) {
    event.preventDefault();
    
    // Use the component's role property
    if (this.isLoggedIn && this.role === 'USER') {
      this.router.navigate(['/dashboard-user']);
    } else if(this.isLoggedIn){
      this.router.navigate(['/dashboard']);
    } 
  }
}