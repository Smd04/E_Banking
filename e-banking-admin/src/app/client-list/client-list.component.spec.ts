import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ClientListComponent } from './client-list.component';

describe('ClientListComponent', () => {
  let component: ClientListComponent;
  let fixture: ComponentFixture<ClientListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientListComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ClientListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load clients on init', () => {
    expect(component.clients.length).toBeGreaterThan(0);
    expect(component.filteredClients.length).toBe(component.clients.length);
  });

  it('should filter clients by search term', () => {
    const searchEvent = { target: { value: 'Jean' } };
    component.onSearch(searchEvent);

    expect(component.filteredClients.length).toBeLessThanOrEqual(component.clients.length);
    expect(component.filteredClients.every(client =>
      client.name.toLowerCase().includes('jean') ||
      client.email.toLowerCase().includes('jean') ||
      client.username.toLowerCase().includes('jean') ||
      client.city.toLowerCase().includes('jean')
    )).toBeTruthy();
  });

  it('should filter clients by status', () => {
    const statusEvent = { target: { value: 'ACTIVE' } };
    component.onStatusFilter(statusEvent);

    expect(component.filteredClients.every(client => client.status === 'ACTIVE')).toBeTruthy();
  });

  it('should filter clients by type', () => {
    const typeEvent = { target: { value: 'courant' } };
    component.onTypeFilter(typeEvent);

    expect(component.filteredClients.every(client => client.type === 'courant')).toBeTruthy();
  });

  it('should calculate total clients correctly', () => {
    expect(component.getTotalClients()).toBe(component.clients.length);
  });

  it('should calculate active clients correctly', () => {
    const activeCount = component.clients.filter(client => client.status === 'ACTIVE').length;
    expect(component.getActiveClients()).toBe(activeCount);
  });

  it('should calculate total EUR deposits correctly', () => {
    const totalEUR = component.clients
      .filter(client => client.currency === 'EUR')
      .reduce((total, client) => total + client.initialDeposit, 0);
    expect(component.getTotalDepositsEUR()).toBe(totalEUR);
  });

  it('should calculate total USD deposits correctly', () => {
    const totalUSD = component.clients
      .filter(client => client.currency === 'USD')
      .reduce((total, client) => total + client.initialDeposit, 0);
    expect(component.getTotalDepositsUSD()).toBe(totalUSD);
  });


  it('should return correct status class', () => {
    expect(component.getStatusClass('ACTIVE')).toBe('status-active');
    expect(component.getStatusClass('INACTIVE')).toBe('status-inactive');
  });


});
