// src/app/components/contract-management/contract-management.component.ts
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ContractService } from '../services/contract.service';
import { ContractRequest } from '../models/contract-request';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-contract-management',
  standalone: true,
  imports: [ CommonModule, ReactiveFormsModule],
  templateUrl: './create-contract.component.html',
  styleUrls: ['./create-contract.component.css']
})
export class CreateContractComponent {
  contractForm: FormGroup;
  isLoading = false;
  successMessage = '';
  errorMessage = '';

  contractTypes = [
    { value: 'Épargne', label: 'Épargne' },
    { value: 'Crédit', label: 'Crédit' },
    { value: 'Assurance', label: 'Assurance' },
    { value: 'Investissement', label: 'Investissement' }
  ];

  statusOptions = [
    { value: 'Actif', label: 'Actif' },
    { value: 'En attente', label: 'En attente' },
    { value: 'Suspendu', label: 'Suspendu' },
    { value: 'Terminé', label: 'Terminé' }
  ];

  constructor(
    private fb: FormBuilder,
    private contractService: ContractService
  ) {
    this.contractForm = this.fb.group({
      type: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: [''],
      amount: ['', [Validators.required, Validators.min(0)]],
      status: ['', Validators.required],
      userId: ['', [Validators.required, Validators.min(1)]],
      agentId: ['', [Validators.required, Validators.min(1)]],
      bankId: ['', [Validators.required, Validators.min(1)]]
    });
  }

  onSubmit() {
    if (this.contractForm.valid) {
      this.isLoading = true;
      this.successMessage = '';
      this.errorMessage = '';

      const contractRequest: ContractRequest = {
        type: this.contractForm.value.type,
        startDate: this.contractForm.value.startDate,
        endDate: this.contractForm.value.endDate || undefined,
        amount: Number(this.contractForm.value.amount),
        status: this.contractForm.value.status,
        userId: Number(this.contractForm.value.userId),
        agentId: Number(this.contractForm.value.agentId),
        bankId: Number(this.contractForm.value.bankId)
      };

      this.contractService.createContract(contractRequest).subscribe({
        next: (response) => {
          this.isLoading = false;
          if (response.message) {
            this.successMessage = response.message;
            this.contractForm.reset();
          }
        },
        error: (error) => {
          this.isLoading = false;
          this.errorMessage = error.error?.error || 'Une erreur est survenue lors de la création du contrat';
          console.error('Erreur:', error);
        }
      });
    } else {
      this.markFormGroupTouched();
    }
  }

  private markFormGroupTouched() {
    Object.keys(this.contractForm.controls).forEach(key => {
      const control = this.contractForm.get(key);
      control?.markAsTouched();
    });
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.contractForm.get(fieldName);
    return !!(field && field.invalid && field.touched);
  }

  getFieldError(fieldName: string): string {
    const field = this.contractForm.get(fieldName);
    if (field?.errors) {
      if (field.errors['required']) {
        return `Le champ ${fieldName} est requis`;
      }
      if (field.errors['min']) {
        return `La valeur doit être supérieure à ${field.errors['min'].min}`;
      }
    }
    return '';
  }
}
