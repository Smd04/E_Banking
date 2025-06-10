import { Component , OnInit} from '@angular/core';
import {HeaderComponent} from '../../Components/header/header.component';
import {FormsModule} from '@angular/forms';
import {AbonnementService} from '../../services/client_service/Abonnement.service';
import {Abonnement} from '../../models/models-client/Abonnement';
import {RouterModule} from '@angular/router';
import {CompteServiceService} from '../../services/client_service/compte-service.service';
import {Compte} from '../../models/models-client/Compte';
import {CommonModule} from '@angular/common';
import { ChatbotComponent } from '../../chatbot/chatbot.component';


@Component({
  selector: 'app-abonnement',
  imports: [
    HeaderComponent,
    FormsModule,
    RouterModule,
    CommonModule,
    ChatbotComponent
  ],
  templateUrl: './abonnement.component.html',
  standalone: true,
  styleUrl: './abonnement.component.css'
})
export class AbonnementComponent implements  OnInit{
  compteNumber !: Compte;
  type= '';
  referenceAbonne='';
  erreurMessage: string = '';
  dateProchainCheck: Date=new Date();
  comptes:Compte[] =[];

  constructor(private abonnementService: AbonnementService ,private compteService:CompteServiceService) {}

  ngOnInit():void{
    this.compteService.getComptesByClient().subscribe(data => {
      this.comptes = data;
      console.log('Les comptes:', this.comptes);
    });
  }

  verifierAbonnement() {
    const factureDetails :Abonnement= {
      compteUser: this.compteNumber.accountNumber,
      dateProchainCheck: new Date(this.dateProchainCheck),
      referenceClient: this.referenceAbonne,
      type: this.type,
    }
    this.erreurMessage = '';

    if (!this.referenceAbonne) {
      this.erreurMessage = 'Veuillez entrer une référence';
      return;
    }
    console.log(this.erreurMessage);

    this.abonnementService.valideAbonnement(factureDetails).subscribe({
      next: (data) => alert("Votre Abonnement est valider avec Success"),
      error: (err) => alert('Référence invalide ou erreur API')
    });
  }

}
