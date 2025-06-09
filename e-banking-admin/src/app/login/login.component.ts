import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username = '';
  password = '';

  constructor(private authService: AuthService, private router: Router) {}

  onLogin() {
    this.authService.login({ username: this.username, password: this.password })
      .subscribe({
        next: () => {

          const role: string | null = this.authService.getRole();
          console.log('Role reçu:', role);
          console.log("token",localStorage.getItem('token'));

          if (role === 'ADMIN') {
            this.router.navigate(['/dashboard']).then();
          } else if (role === 'SUPADMIN') {
            this.router.navigate(['/dashboard']).then();
          } else if (role === 'USER') {
            this.router.navigate(['/dashboard-user']).then();
          } else if (role === 'Agent') {
            this.router.navigate(['/dashboard-Banque']).then();
          }

        },
        error: (err: any) => {
          console.error('Erreur login:', err);

        }
      });
  }
}
