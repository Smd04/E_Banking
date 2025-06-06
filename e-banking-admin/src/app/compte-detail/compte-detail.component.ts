import { Component } from '@angular/core';

import { FormsModule } from '@angular/forms';
import { DecimalPipe } from '@angular/common';
import {ActivatedRoute, RouterModule} from '@angular/router';
import { CommonModule } from '@angular/common';

import {
  Chart,
  LineController,
  LineElement,
  PointElement,
  LinearScale,
  Title,
  CategoryScale,
  Tooltip,
  Legend,
  Filler
} from 'chart.js';
import {Compte} from '../models/models-client/Compte';
import {Transaction} from '../models/models-client/Transaction';
import {CompteServiceService} from '../services/client_service/compte-service.service';

@Component({
  selector: 'app-compte-detail',
  imports: [
    FormsModule,
    DecimalPipe,
    CommonModule,
    RouterModule
  ],
  templateUrl: './compte-detail.component.html',
  styleUrl: './compte-detail.component.css'
})
export class CompteDetailComponent {
  compteActif!: Compte;
  transaction: Transaction[] = [];
  alerteDepenses = false;
  chart!: Chart;
  RecentTransaction: Transaction[] = [];

  constructor(
    private compteService: CompteServiceService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    Chart.register(
      LineController,
      LineElement,
      PointElement,
      LinearScale,
      Title,
      CategoryScale,
      Tooltip,
      Legend,
      Filler
    );

    if (!isNaN(id)) {
      this.compteService.getComptesById(id).subscribe(data => {
        this.compteActif = data;
        console.log("compteActif " ,this.compteActif);
        console.log("compte Actif RIB",this.compteActif.AccountNumber);
        this.chargerDetailsCompte();
        this.getRecentTransaction();
        this.afficherGraphiqueSoldeMensuel();
      });
    }
  }

  chargerDetailsCompte() {
    if (!this.compteActif) return;

    this.compteService.getTransactionByCompteId(this.compteActif.id).subscribe(data => {
      this.transaction = data;
      this.alerteDepenses = this.transaction.some(t => t.amount < -500);
    });
  }

  supprimerCompte() {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce compte ?')) {
      this.compteService.supprimerCompte(this.compteActif.id).subscribe(() => {
        alert('Compte supprimé.');

      });
    }
  }

  changerStatutCompte() {
    const nouveauStatut = this.compteActif.status ? 'INACTIF' : 'ACTIF';
    this.compteService.activerOuDesactiverCompte(this.compteActif.id, nouveauStatut).subscribe(() => {
      this.compteActif.status = nouveauStatut;
    });
  }

  modifierCompte() {
    alert('Tu sur que tu veut changer...');
  }

  afficherGraphiqueSoldeMensuel(): void {
    if (!this.compteActif) return;

    this.compteService.getMonthlyBalance(this.compteActif.id).subscribe(data => {
      const labels: string[] = Object.keys(data);
      const values: number[] = Object.values(data).map(v => Number(v)); // <- Cast en number

      if (this.chart) {
        this.chart.destroy();
      }

      this.chart = new Chart('chartSoldeMensuel', {
        type: 'line',
        data: {
          labels: labels,
          datasets: [{
            label: 'Solde du compte (DH)',
            data: values,
            borderColor: 'rgba(75, 192, 192, 1)',
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            fill: true,
            tension: 0.3
          }]
        },
        options: {
          responsive: true,
          scales: {
            y: {
              beginAtZero: false
            }
          }
        }
      });

    }, error => {
      console.error("Erreur lors du chargement des soldes mensuels", error);
    });
  }


  toDate(array: [number, number, number, number?, number?]): Date {
    const [year, month, day, hour = 0, minute = 0] = array;
    return new Date(year, month - 1, day, hour, minute);
  }

  getRecentTransaction() {
    if (!this.compteActif) return;
    this.compteService.getRecentTransactionByCompteId(this.compteActif.id).subscribe(data => {
      this.RecentTransaction = data;
      console.log("Recent Transaction", this.RecentTransaction);
    });
  }
  toDateString(dateArray: [number, number, number, number?, number?]): string {
    const [year, month, day] = dateArray;
    const formattedMonth = month.toString().padStart(2, '0');
    const formattedDay = day.toString().padStart(2, '0');
    return `${year}-${formattedMonth}-${formattedDay}`;
  }
}
