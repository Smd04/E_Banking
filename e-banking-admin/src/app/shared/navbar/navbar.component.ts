import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common'; // Fixed import
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
  fullname='';

  constructor(private authService: AuthService, private router: Router) {
    this.authService.currentUser$.subscribe((user: User | null) => {
      this.isLoggedIn = !!user;
      if (user) {
        this.fullname = user.firstName+user.lastName || '';
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
}