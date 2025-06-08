// src/app/components/contract-list/contract-list.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ContractService } from '../services/contract.service';
import { Contract } from '../models/contract';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-contract-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './contract-list.component.html',
  styleUrls: ['./contract-list.component.css']
})
export class ContractListComponent implements OnInit {
  contracts: Contract[] = [];
  filteredContracts: Contract[] = [];
  isLoading = false;
  errorMessage = '';

  // Filter properties
  filterType = '';
  filterStatus = '';
  searchTerm = '';

  contractTypes = [
    { value: '', label: 'Tous les types' },
    { value: 'Épargne', label: 'Épargne' },
    { value: 'Crédit', label: 'Crédit' },
    { value: 'Assurance', label: 'Assurance' },
    { value: 'Investissement', label: 'Investissement' }
  ];

  statusOptions = [
    { value: '', label: 'Tous les statuts' },
    { value: 'Actif', label: 'Actif' },
    { value: 'En attente', label: 'En attente' },
    { value: 'Suspendu', label: 'Suspendu' },
    { value: 'Terminé', label: 'Terminé' }
  ];

  constructor(private contractService: ContractService) {}

  ngOnInit() {
    this.loadContracts();
  }

  loadContracts() {
    this.isLoading = true;
    this.errorMessage = '';

    this.contractService.getAllContracts().subscribe({
      next: (contracts) => {
        this.contracts = contracts;
        this.filteredContracts = contracts;
        this.isLoading = false;
      },
      error: (error) => {
        this.isLoading = false;
        this.errorMessage = 'Erreur lors du chargement des contrats';
        console.error('Erreur:', error);
      }
    });
  }

  applyFilters() {
    this.filteredContracts = this.contracts.filter(contract => {
      const matchesType = !this.filterType || contract.type === this.filterType;
      const matchesStatus = !this.filterStatus || contract.status === this.filterStatus;
      const matchesSearch = !this.searchTerm ||
        contract.type.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        contract.status.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        contract.id.toString().includes(this.searchTerm);

      return matchesType && matchesStatus && matchesSearch;
    });
  }

  onFilterChange() {
    this.applyFilters();
  }

  onSearch() {
    this.applyFilters();
  }

  clearFilters() {
    this.filterType = '';
    this.filterStatus = '';
    this.searchTerm = '';
    this.filteredContracts = this.contracts;
  }

  refreshContracts() {
    this.loadContracts();
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'Actif':
        return 'status-active';
      case 'En attente':
        return 'status-pending';
      case 'Suspendu':
        return 'status-suspended';
      case 'Terminé':
        return 'status-completed';
      default:
        return '';
    }
  }

  formatDate(date: string): string {
    return new Date(date).toLocaleDateString('fr-FR');
  }

  formatAmount(amount: number): string {
    return new Intl.NumberFormat('fr-FR', {
      style: 'currency',
      currency: 'MAD'
    }).format(amount);
  }
}
