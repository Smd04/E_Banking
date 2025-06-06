// bank-profile.component.ts
import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {DatePipe, NgForOf, NgIf} from '@angular/common';
import {HeaderComponent} from '../../Components/header/header.component';
import {RouterModule} from '@angular/router';

@Component({
  selector: 'app-bank-profile',
  templateUrl: './profile.component.html',
  standalone: true,
  imports: [
    DatePipe,
    ReactiveFormsModule,
    NgIf,
    NgForOf,
    HeaderComponent,
    RouterModule,
  ],
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  profileForm!: FormGroup;
  isEditing = false;
  isLoading = false;
  showSecuritySection = false;

  client = {
    id: 'CLT-789456',
    title: 'M.',
    firstName: 'Pierre',
    lastName: 'Martin',
    birthDate: '1985-04-12',
    email: 'pierre.martin@example.com',
    phone: '+33 6 12 34 56 78',
    address: '45 Avenue des Champs-Élysées, 75008 Paris',
    profession: 'Ingénieur Informatique',
    monthlyIncome: 5500,
    clientSince: '2018-07-15',
    lastLogin: '2023-10-28 14:22:45',
    status: 'VIP Gold',
    accounts: [
      { id: 'ACC-123456', type: 'Compte Courant', balance: 12450.75, currency: 'EUR', iban: 'FR76 3000 1000 1234 5678 9012 345' },
      { id: 'ACC-789012', type: 'Livret A', balance: 8750.20, currency: 'EUR', iban: 'FR76 3000 1000 9876 5432 1098 765' },
      { id: 'ACC-345678', type: 'Compte Épargne Logement', balance: 32500.00, currency: 'EUR', iban: 'FR76 3000 1000 2468 1357 9024 681' }
    ],
    cards: [
      { id: 'CARD-456789', type: 'Visa Infinite', number: '**** **** **** 1234', expiry: '12/25', status: 'Active', limit: 7500 },
      { id: 'CARD-901234', type: 'MasterCard Gold', number: '**** **** **** 5678', expiry: '08/24', status: 'Active', limit: 3000 }
    ]
  };

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm(): void {
    this.profileForm = this.fb.group({
      title: [this.client.title],
      firstName: [this.client.firstName, Validators.required],
      lastName: [this.client.lastName, Validators.required],
      birthDate: [this.client.birthDate, Validators.required],
      email: [this.client.email, [Validators.required, Validators.email]],
      phone: [this.client.phone, Validators.required],
      address: [this.client.address, Validators.required],
      profession: [this.client.profession]
    });
  }

  toggleEdit(): void {
    this.isEditing = !this.isEditing;
    if (!this.isEditing) {
      this.profileForm.reset({
        title: this.client.title,
        firstName: this.client.firstName,
        lastName: this.client.lastName,
        birthDate: this.client.birthDate,
        email: this.client.email,
        phone: this.client.phone,
        address: this.client.address,
        profession: this.client.profession
      });
    }
  }

  saveProfile(): void {
    if (this.profileForm.valid) {
      this.isLoading = true;

      // Simulation de sauvegarde
      setTimeout(() => {
        const formValue = this.profileForm.value;

        this.client = {
          ...this.client,
          title: formValue.title,
          firstName: formValue.firstName,
          lastName: formValue.lastName,
          birthDate: formValue.birthDate,
          email: formValue.email,
          phone: formValue.phone,
          address: formValue.address,
          profession: formValue.profession
        };

        this.isEditing = false;
        this.isLoading = false;
      }, 1500);
    }
  }

  getTotalBalance(): number {
    return this.client.accounts.reduce((sum, account) => sum + account.balance, 0);
  }

  formatCurrency(value: number): string {
    return new Intl.NumberFormat('fr-FR', {
      style: 'currency',
      currency: 'EUR',
      minimumFractionDigits: 2
    }).format(value);
  }

  maskAccountNumber(iban: string): string {
    const visibleDigits = 4;
    return iban.slice(0, 4) + ' **** **** **** ' + iban.slice(-3);
  }
}
