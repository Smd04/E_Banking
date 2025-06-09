import { Component ,OnInit} from '@angular/core';
import {HeaderComponent} from '../../Components/header/header.component';
import {FormsModule} from '@angular/forms';
import {FactureService} from '../../services/client_service/Facture.service';
import {Facture} from '../../models/models-client/Facture';
import {RouterModule} from '@angular/router';
import {Compte} from '../../models/models-client/Compte';
import {CompteServiceService} from '../../services/client_service/compte-service.service';
import {CommonModule} from '@angular/common';


@Component({
  selector: 'app-facture',
  imports: [
    HeaderComponent,
    FormsModule,
    RouterModule,
    CommonModule
  ],
  templateUrl: './facture.component.html',
  standalone: true,
  styleUrl: './facture.component.css'
})
export class FactureComponent implements  OnInit{
  constructor(private factureService: FactureService, private compteService:CompteServiceService) {}
  compteUser='';
  dateProchainCheck=new Date();
  reference= '';
  type= '';

  erreurMessage: string = '';

  comptes!:Compte[];
  ngOnInit():void{
    this.compteService.getComptesByClient().subscribe(data => {
      this.comptes = data;
      console.log('Les comptes:', this.comptes);
    });
  }



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
      next: (message: any) => {
        alert(message);},
      error: (err) => alert('Référence invalide ou erreur API')
    });
  }


}
