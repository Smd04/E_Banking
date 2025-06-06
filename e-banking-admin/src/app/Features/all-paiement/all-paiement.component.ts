import { Component, OnInit } from '@angular/core';
import { DatePipe, NgForOf } from '@angular/common';
import { HeaderComponent } from '../../Components/header/header.component';
import { ListPaiementService } from '../../services/client_service/listPaiement.service';
import { Paiement } from '../../models/models-client/paiement';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-all-paiement',
  imports: [
    DatePipe,
    HeaderComponent,
    NgForOf,
    RouterModule
  ],
  templateUrl: './all-paiement.component.html',
  standalone: true,
  styleUrls: ['./all-paiement.component.css']
})
export class AllPaiementComponent implements OnInit {
  allPaiement: Paiement[] = [];

  constructor(private paiementService: ListPaiementService) {}

  ngOnInit() {
    this.paiementService.getAllPaiement().subscribe(
      data => {
        this.allPaiement = data;
      }
    );
  }
}