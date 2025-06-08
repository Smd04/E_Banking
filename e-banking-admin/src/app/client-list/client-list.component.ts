import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import { ClientService, Client } from '../services/client.service'; // Adjust path as needed

@Component({
  selector: 'app-client-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit, OnDestroy {
  clients: Client[] = [];
  filteredClients: Client[] = [];
  searchTerm: string = '';
  selectedStatus: string = 'all';
  selectedType: string = 'all';

  isLoading = false;
  error = '';

  private clientsSubscription: Subscription = new Subscription();

  constructor(private clientService: ClientService) {}

  ngOnInit() {
    this.loadClients();
    // Optionally sync with backend on component init
    this.syncWithBackend();
  }

  ngOnDestroy() {
    this.clientsSubscription.unsubscribe();
  }

  loadClients() {
    // Subscribe to client changes for real-time updates
    this.clientsSubscription = this.clientService.getClients().subscribe(clients => {
      this.clients = clients;
      this.applyFilters();
    });
  }

  // Optional: Sync with backend
  syncWithBackend() {
    this.isLoading = true;
    this.error = '';
    this.clientService.syncWithBackend();
    setTimeout(() => {
      this.isLoading = false;
    }, 1000);
  }

  onSearch(event: any) {
    this.searchTerm = event.target.value.toLowerCase();
    this.applyFilters();
  }

  onStatusFilter(event: any) {
    this.selectedStatus = event.target.value;
    this.applyFilters();
  }

  onTypeFilter(event: any) {
    this.selectedType = event.target.value;
    this.applyFilters();
  }

  applyFilters() {
    this.filteredClients = this.clients.filter(client => {
      const matchesSearch =
        client.name.toLowerCase().includes(this.searchTerm) ||
        client.email.toLowerCase().includes(this.searchTerm) ||
        client.username.toLowerCase().includes(this.searchTerm) ||
        client.city.toLowerCase().includes(this.searchTerm);

      const matchesStatus = this.selectedStatus === 'all' || client.status === this.selectedStatus;
      const matchesType = this.selectedType === 'all' || client.type === this.selectedType;

      return matchesSearch && matchesStatus && matchesType;
    });
  }


  getStatusClass(status: string): string {
    return status === 'ACTIVE' ? 'status-active' : 'status-inactive';
  }

  getTotalClients(): number {
    return this.clients.length;
  }

  getActiveClients(): number {
    return this.clients.filter(client => client.status === 'ACTIVE').length;
  }

  getTotalDepositsEUR(): number {
    return this.clients
      .filter(client => client.currency === 'EUR')
      .reduce((total, client) => total + client.initialDeposit, 0);
  }

  getTotalDepositsUSD(): number {
    return this.clients
      .filter(client => client.currency === 'USD')
      .reduce((total, client) => total + client.initialDeposit, 0);
  }

}