import { Component } from '@angular/core';
import {NgClass} from '@angular/common';
import {FormsModule} from '@angular/forms';

import {HeaderComponent} from '../../Components/header/header.component';
import {VirementService} from '../../services/client_service/virement.service';
import {AuthService} from '../../services/auth.service';
import {RouterModule} from '@angular/router';

@Component({
  selector: 'app-otp-input',
  templateUrl: './otp-input-virement.component.html',
  standalone: true,
  imports: [
    NgClass,
    FormsModule,
    HeaderComponent,
    RouterModule,
  ],
  styleUrls: ['./otp-input-virement.component.css']
})
export class OtpInputVirementComponent {

  constructor(private virementService: VirementService, private authService: AuthService,) {
  }

  otpCode: string = '';
  isValid: boolean = false;
  message: string = '';
  isCodeValid: boolean = false;

  onInputChange() {
    this.isValid = /^\d{6}$/.test(this.otpCode);
    this.message = '';
  }
  validateOtp() {
    const token = localStorage.getItem('token');
    const phone = this.authService.getPhoneNumber();
    this.virementService.validerOtp(phone, this.otpCode).subscribe({
      next: (data) => alert(data),
      error: err => alert('Code invalide ou expiré.')
    });
  }
}
