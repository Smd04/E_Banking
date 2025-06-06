import { Component } from '@angular/core';
import {HeaderComponent} from '../../Components/header/header.component';
import {FormsModule} from '@angular/forms';
import {AbonnementService} from '../../services/client_service/Abonnement.service';
import {Abonnement} from '../../models/models-client/Abonnement';
import {RouterModule} from '@angular/router';


@Component({
  selector: 'app-abonnement',
  imports: [
    HeaderComponent,
    FormsModule,
    RouterModule
  ],
  templateUrl: './abonnement.component.html',
  standalone: true,
  styleUrl: './abonnement.component.css'
})
export class AbonnementComponent {
  compteNumber='';
  type= '';
  referenceAbonne='';
  erreurMessage: string = '';
  dateProchainCheck: Date=new Date();


constructor(private abonnementService: AbonnementService) {}


  verifierAbonnement() {
    const factureDetails :Abonnement= {
      compteUser: this.compteNumber,
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
