import { Component } from '@angular/core';
import {NgClass} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {HeaderComponent} from '../../Components/header/header.component';
import {RechargeService} from '../../services/client_service/recharge.service';
import {AuthService} from '../../services/auth.service';
import {RouterModule} from '@angular/router';
import { ChatbotComponent } from '../../chatbot/chatbot.component';


@Component({
  selector: 'app-otp-input',
  templateUrl: './otp-input-recharge.component.html',
  standalone: true,
  imports: [
    NgClass,
    FormsModule,
    HeaderComponent,
    RouterModule,
    ChatbotComponent
  ],
  styleUrls: ['./otp-input-recharge.component.css']
})
export class OtpInputRechargeComponent {

  constructor(private rechargeService: RechargeService, private authService: AuthService,) {
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
    this.rechargeService.validerOtp(this.otpCode).subscribe({
      next: (message: any) => {
        alert(message);
      } ,
      error: err => alert('Erreur : ' + err.message)
    });
  }
}
