import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { ClientEnrolmentRequest } from '../models/client-enrolment-request';

// Client interface for local state management
export interface Client {
  id?: number;
  name: string;
  username: string;
  email: string;
  phone: string;
  type: string;
  currency: string;
  initialDeposit: number;
  status: string;
  city: string;
  address: string;
  dateEnrolled?: Date;
}

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private apiUrl = 'http://localhost:8080/project_e_banking_war/api/banque';

  // Local client state management
  private clients: Client[] = [
    // Some initial mock data - you can remove these if you don't want default clients

  ];

  private clientsSubject = new BehaviorSubject<Client[]>(this.clients);
  private nextId = 1; // Start from 2 since we have 1 mock client

  constructor(private http: HttpClient) {
    this.loadFromStorage();
  }

  // Backend API Methods
  enrolClient(clientData: ClientEnrolmentRequest): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/enrol-client`, clientData).pipe(
      tap(response => {
        // If backend enrollment is successful, add to local state
        console.log('Client enrolled successfully on backend:', response);
        this.addClientToLocalState(clientData, response);
      }),
      catchError(error => {
        console.error('Error enrolling client on backend:', error);
        // You can still add to local state even if backend fails (offline mode)
        // this.addClientToLocalState(clientData);
        return throwError(error);
      })
    );
  }

  // Get all clients from backend (if you have this endpoint)
  getAllClientsFromBackend(): Observable<Client[]> {
    return this.http.get<Client[]>(`${this.apiUrl}/clients`);
  }

  // Update client on backend (if you have this endpoint)
  updateClientOnBackend(id: number, clientData: Partial<Client>): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/clients/${id}`, clientData);
  }

  // Delete client on backend (if you have this endpoint)
  deleteClientFromBackend(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/clients/${id}`);
  }

  // Local State Management Methods (for immediate UI updates)

  // Get all clients as observable for reactive updates
  getClients(): Observable<Client[]> {
    return this.clientsSubject.asObservable();
  }

  // Get all clients as array for immediate access
  getClientsSync(): Client[] {
    return [...this.clients];
  }

  // Add client to local state (called after successful backend enrollment)
  private addClientToLocalState(clientData: ClientEnrolmentRequest | any, backendResponse?: any): Client {
    const newClient: Client = {
      id: backendResponse?.id || this.nextId++,
      name: clientData.name || clientData.nom,
      username: clientData.username || clientData.nomUtilisateur,
      email: clientData.email,
      phone: clientData.phone || clientData.telephone,
      type: clientData.type || clientData.typeCompte,
      currency: clientData.currency || clientData.devise,
      initialDeposit: clientData.initialDeposit || clientData.depotInitial,
      status: clientData.status || 'ACTIVE',
      city: clientData.city || clientData.ville,
      address: clientData.address || clientData.adresse,
      dateEnrolled: new Date()
    };

    this.clients.push(newClient);
    this.saveToStorage();
    this.clientsSubject.next([...this.clients]);
    return newClient;
  }

  // Add client directly to local state (for offline mode or testing)
  addClientLocal(clientData: Omit<Client, 'id' | 'dateEnrolled'>): Client {
    const newClient: Client = {
      ...clientData,
      id: this.nextId++,
      dateEnrolled: new Date(),
      status: clientData.status || 'ACTIVE'
    };

    this.clients.push(newClient);
    this.saveToStorage();
    this.clientsSubject.next([...this.clients]);
    return newClient;
  }

  // Update client in local state
  updateClientLocal(id: number, updatedClient: Partial<Client>): boolean {
    const index = this.clients.findIndex(client => client.id === id);
    if (index !== -1) {
      this.clients[index] = { ...this.clients[index], ...updatedClient };
      this.saveToStorage();
      this.clientsSubject.next([...this.clients]);
      return true;
    }
    return false;
  }

  // Delete client from local state
  deleteClientLocal(id: number): boolean {
    const index = this.clients.findIndex(client => client.id === id);
    if (index !== -1) {
      this.clients.splice(index, 1);
      this.saveToStorage();
      this.clientsSubject.next([...this.clients]);
      return true;
    }
    return false;
  }

  // Combined method: Delete client from both backend and local state
  deleteClient(id: number): Observable<any> {
    // First delete from backend
    return this.deleteClientFromBackend(id).pipe(
      tap(() => {
        // If backend deletion successful, remove from local state
        this.deleteClientLocal(id);
      }),
      catchError(error => {
        console.error('Error deleting client from backend:', error);
        // Optionally still delete from local state
        // this.deleteClientLocal(id);
        return throwError(error);
      })
    );
  }

  // Utility Methods
  getClientById(id: number): Client | undefined {
    return this.clients.find(client => client.id === id);
  }

  getClientsByStatus(status: string): Client[] {
    return this.clients.filter(client => client.status === status);
  }

  getTotalClients(): number {
    return this.clients.length;
  }

  getActiveClients(): number {
    return this.clients.filter(client => client.status === 'ACTIVE').length;
  }

  getTotalDepositsEUR(): number {
    const clients = this.clientsSubject.value;
    return clients
      .filter(client => client.currency === 'EUR')
      .reduce((total, client) => total + client.initialDeposit, 0);
  }

  getTotalDepositsUSD(): number {
    const clients = this.clientsSubject.value;
    return clients
      .filter(client => client.currency === 'USD')
      .reduce((total, client) => total + client.initialDeposit, 0);
  }


  // Storage Methods (for persistence)
  private saveToStorage(): void {
    try {
      const clientsData = {
        clients: this.clients,
        nextId: this.nextId
      };
      localStorage.setItem('banking_clients', JSON.stringify(clientsData));
    } catch (error) {
      console.warn('Could not save to localStorage:', error);
    }
  }

  private loadFromStorage(): void {
    try {
      const saved = localStorage.getItem('banking_clients');
      if (saved) {
        const data = JSON.parse(saved);
        if (data.clients && Array.isArray(data.clients)) {
          this.clients = data.clients.map((client: Client) => ({
            ...client,
            dateEnrolled: client.dateEnrolled ? new Date(client.dateEnrolled) : new Date()
          }));
          this.nextId = data.nextId || this.clients.length + 1;
          this.clientsSubject.next([...this.clients]);
        }
      }
    } catch (error) {
      console.warn('Could not load from localStorage:', error);
    }
  }

  // Sync with backend (call this when app starts or when you want to sync)
  syncWithBackend(): void {
    this.getAllClientsFromBackend().subscribe({
      next: (backendClients) => {
        this.clients = backendClients;
        this.saveToStorage();
        this.clientsSubject.next([...this.clients]);
        console.log('Synced with backend successfully');
      },
      error: (error) => {
        console.warn('Could not sync with backend:', error);
      }
    });
  }

  // Clear all local clients
  clearAllClients(): void {
    this.clients = [];
    this.nextId = 1;
    this.saveToStorage();
    this.clientsSubject.next([]);
  }
}
