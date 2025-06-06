import { Component } from '@angular/core';
import {HeaderComponent} from '../../Components/header/header.component';
import {FormsModule} from '@angular/forms';
import {FactureService} from '../../services/client_service/Facture.service';
import {Facture} from '../../models/models-client/Facture';
import {RouterModule} from '@angular/router';


@Component({
  selector: 'app-facture',
  imports: [
    HeaderComponent,
    FormsModule,
    RouterModule,
  ],
  templateUrl: './facture.component.html',
  standalone: true,
  styleUrl: './facture.component.css'
})
export class FactureComponent {
  constructor(private factureService: FactureService) {}
  compteUser='';
  dateProchainCheck=new Date();
    reference= '';
    type= '';

  erreurMessage: string = '';





  verifierFacture() {
    const factureDetails :Facture= {
      compteUser: this.compteUser,
      dateProchainCheck: new Date(this.dateProchainCheck),
      referenceClient: this.reference,
      type: this.type,
    }
    this.erreurMessage = '';

    if (!this.reference) {
      this.erreurMessage = 'Veuillez entrer une référence';
      return;
    }
    console.log(this.erreurMessage);

    this.factureService.valideFacture(factureDetails).subscribe({
      next: (data) => alert("Votre Facture est valider avec Success"),
      error: (err) => alert('Référence invalide ou erreur API')
    });
  }


}
