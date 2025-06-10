// referral.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReferralService } from '../services/referral.service';
import { AuthService } from '../services/auth.service';
import {HeaderComponent} from './../Components/header/header.component';
import { ChatbotComponent } from './../chatbot/chatbot.component';


@Component({
  selector: 'app-referral',
  standalone: true,
  imports: [CommonModule, HeaderComponent,ChatbotComponent],
  templateUrl: './referral.component.html',
  styleUrls: ['./referral.component.scss']
})
export class ReferralComponent implements OnInit {
  commission: number | null = null;
  errorMessage?: string;
  isLoading = true;
  referralDisabled = false;

  constructor(
    private referralService: ReferralService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const userId = this.authService.getUserId();
    if (!userId) {
      this.errorMessage = 'User not authenticated';
      this.isLoading = false;
      return;
    }

    this.referralService.getReferralCommission(+userId).subscribe({
      next: (response) => {
        this.commission = response.commission;
        this.isLoading = false;
        this.referralDisabled = false;
      },
      error: (err) => {
        this.errorMessage = err.message;
        this.isLoading = false;
        this.referralDisabled = err.message.includes('not allowed to refer');
        console.error('Error:', err);
      }      
    });
  }
}