import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ClientService } from '../services/client.service';
import { ClientEnrolmentRequest } from '../models/client-enrolment-request';

@Component({
  selector: 'app-enrol-client',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './enrol-client.component.html',
  styleUrls: ['./enrol-client.component.css']
})
export class EnrolClientComponent {
  client: ClientEnrolmentRequest = {
    name: '',
    username: '',
    email: '',
    phone: '',
    password: '',
    type: '',
    currency: '',
    initialDeposit: 0,
    address: '',
    city: '',
    status: '',
    accountNumber: ''
  };

  successMessage: string = '';
  isSubmitting: boolean = false;

  constructor(private clientService: ClientService) {}

  enrolClient(): void {
    if (!this.isFormValid()) {
      this.successMessage = 'Veuillez remplir tous les champs requis.';
      return;
    }

    this.isSubmitting = true;
    this.successMessage = '';

    this.clientService.enrolClient(this.client).subscribe({
      next: (res: any) => {
        console.log('Réponse du backend :', res);

        // Clear any previous error messages and show success
        if (res?.message) {
          this.successMessage = res.message;
        } else {
          this.successMessage = 'Client enrole avec succes.';
        }

        // Reset form after successful enrollment
        this.resetForm();

        console.log('Client ajouté à la liste locale');
      },
      error: (err: any) => {
        console.error('Erreur lors de l\'enrolement :', err);

        if (err?.error?.error) {
          this.successMessage = 'Erreur : ' + err.error.error;
        } else if (err?.error?.message) {
          this.successMessage = 'Erreur : ' + err.error.message;
        } else if (err?.message) {
          this.successMessage = 'Erreur : ' + err.message;
        } else {
          this.successMessage = 'Erreur lors de l\'envoi du formulaire.';
        }
      },
      complete: () => {
        this.isSubmitting = false;
      }
    });
  }

  private isFormValid(): boolean {
    return !!(
      this.client.name?.trim() &&
      this.client.username?.trim() &&
      this.client.email?.trim() &&
      this.client.phone?.trim() &&
      this.client.password?.trim() &&
      this.client.type?.trim() &&
      this.client.currency?.trim() &&
      this.client.city?.trim() &&
      this.client.address?.trim() &&
      this.client.initialDeposit > 0
    );
  }

  resetForm(): void {
    this.client = {
      name: '',
      username: '',
      email: '',
      phone: '',
      password: '',
      type: '',
      currency: '',
      address: '',
      city: '',
      initialDeposit: 0,
      status: '',
      accountNumber: ''
    };

    // Clear success message after a delay
    setTimeout(() => {
      this.successMessage = '';
    }, 3000);
  }

  // Helper validation methods
  isEmailValid(): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return this.client.email ? emailRegex.test(this.client.email) : false;
  }

  isPhoneValid(): boolean {
    const phoneRegex = /^[0-9\s\-\+\(\)]{10,}$/;
    return this.client.phone ? phoneRegex.test(this.client.phone) : false;
  }

  // Method to clear success message manually
  clearMessage(): void {
    this.successMessage = '';
  }

  // Get message type for styling
  getMessageType(): string {
    if (this.successMessage.toLowerCase().includes('erreur')) {
      return 'error';
    } else if (this.successMessage.toLowerCase().includes('succes') ||
      this.successMessage.toLowerCase().includes('enrole')) {
      return 'success';
    }
    return 'info';
  }
}
