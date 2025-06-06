import { Component, OnInit } from '@angular/core';

import {DatePipe, DecimalPipe, NgClass} from '@angular/common';
import {Router, RouterLink, RouterModule} from '@angular/router';
import {FormsModule} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FilterPipe } from '../pipes/filter.pipe';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import {Compte} from '../models/models-client/Compte';
import {CompteServiceService} from '../services/client_service/compte-service.service';

@Component({
  selector: 'app-comptes-component',
  templateUrl: 'comptes-component.component.html',
  imports: [
    DecimalPipe,
    FormsModule,
    NgClass,
    CommonModule,
    RouterModule,

  ],
  styleUrls: ['./comptes-component.component.css']
})
export class ComptesComponentComponent implements OnInit {


  Comptes: Compte[] = [];
  searchTerm = '';
  isLoading = true;
  currentPage = 1;
  itemsPerPage = 4;

  constructor(private compteService: CompteServiceService, private router:Router) {}

  ngOnInit(): void {
    this.compteService.getComptesByClient().subscribe({
      next: (data :Compte[]) => {
        this.Comptes = data;
        this.isLoading = false;
        console.log("comptes voici les comptes",this.Comptes);
      },
      error: (err: any) => {
        console.error('Erreur de chargement des comptes', err);
        this.isLoading = false;
      }
    });
  }

  get paginatedComptes(): Compte[]{
    const start = (this.currentPage -1) * this.itemsPerPage;
    return this.filtredComptes.slice(start,start+this.itemsPerPage);
  }
  get filtredComptes(): Compte[]{
    return this.Comptes.filter(c =>
    JSON.stringify(c).toLowerCase().includes(this.searchTerm.toLowerCase())
    )
  }
  nextPage(){
    if(this.currentPage * this.itemsPerPage < this.filtredComptes.length){
      this.currentPage++;
    }
  }
  previousPage(){
    if(this.currentPage > 1) {
      this.currentPage--;
    }
  }
  redirectToAjouterCompte(){
    this.router.navigate(['/ajouter-compte']);
      }


  redirectToDetailCompte(idCompte: number){
    this.router.navigate(['/detail-compte', idCompte]);
  }
  exportPDF() {
    const doc = new jsPDF();
    doc.text('Liste des Comptes', 10, 10);

    autoTable(doc, {
      head: [['ID', 'Type', 'Solde', 'Devise', 'Statut']],
      body: this.filtredComptes.map(compte => [
        compte.id,
        compte.type,
        compte.balance.toFixed(2),
        compte.currency,
        compte.status
      ])
    });

    doc.save('comptes.pdf');
  }

}
