import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';

import {Router} from '@angular/router';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  username = '';
  password = '';

  constructor(private authService: AuthService,private router: Router) {
  }

  onLogin() {
    this.authService.login({username: this.username, password: this.password})
      .subscribe({
        next: (res: { accessToken: any; }) => {
          localStorage.setItem('token',res.accessToken);
          console.log('Token reçu:', res.accessToken);
          const role :string | null = this.authService.getRole();
          if(role === 'admin'){
            //rederect ici
          }
          else if(role === 'supadmin'){
            //rederect ic
          }else if(role === 'user')
          {
            this.router.navigate(['/dashboard']).then();
          }
        },
        error: (err: any) => {
          console.error('Erreur login:', err);
        }
      });
  }
}
