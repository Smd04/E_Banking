import { Component, OnInit } from '@angular/core';
import { DatePipe, NgForOf } from '@angular/common';
import { HeaderComponent } from '../../Components/header/header.component';
import { ListPaiementService } from '../../services/client_service/listPaiement.service';
import { Paiement } from '../../models/models-client/paiement';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-historique-paiement',
  imports: [
    DatePipe,
    HeaderComponent,
    NgForOf,
    RouterModule
  ],
  templateUrl: './historique-paiement.component.html',
  standalone: true,
  styleUrls: ['./historique-paiement.component.css']
})
export class HistoriquePaiementComponent implements OnInit {
  recentPaiement: Paiement[] = [];

  constructor(private paiementService: ListPaiementService) {}

  ngOnInit() {
    this.paiementService.getRecentPaiement().subscribe(
      data => {
        this.recentPaiement = data;
      }
    );
  }
}