import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChatbotComponent } from '../../chatbot/chatbot.component';
import { AuthService } from '../../services/auth.service';
import { HttpClientModule } from '@angular/common/http'; // Add this

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterModule, ChatbotComponent, CommonModule, HttpClientModule], // Add HttpClientModule
  templateUrl: './dashboard-Banque.component.html',
  styleUrls: ['./dashboard-Banque.component.css'],
  providers: [AuthService] // Explicitly provide AuthService here
})
export class DashboardBanqueComponent implements OnInit {
  role: string | null = null;

  constructor(private authService: AuthService) {
    console.log('AuthService injected:', authService); 
  }

  ngOnInit(): void {
    this.role = this.authService.getRole();
    console.log('User role:', this.role); 
  }
}