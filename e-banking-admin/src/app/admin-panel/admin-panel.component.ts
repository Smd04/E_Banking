// admin-panel.component.ts
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-admin-panel',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent {
  constructor(private router: Router) {}

  navigateToClientManagement() {
    this.router.navigate(['/enrol-client']);
  }

  navigateToCreateContract() {
    this.router.navigate(['/create-contract']);
  }
  navigateToSeeClients() {
    this.router.navigate(['/client-list']);
  }
  navigateToSeeContracts() {
    this.router.navigate(['/contract-list']);
  }


}
