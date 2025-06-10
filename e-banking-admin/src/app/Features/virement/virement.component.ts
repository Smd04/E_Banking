import { Component ,OnInit } from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {HeaderComponent} from '../../Components/header/header.component';

import {Router, RouterModule} from '@angular/router';

import {FormsModule} from '@angular/forms';
import {VirementService} from '../../services/client_service/virement.service';
import {AuthService} from '../../services/auth.service';
import {CompteServiceService} from '../../services/client_service/compte-service.service';
import {Compte} from '../../models/models-client/Compte';
import { ChatbotComponent } from '../../chatbot/chatbot.component';

@Component({
  selector: 'app-virement',
  imports: [
    NgOptimizedImage,
    HeaderComponent,
    FormsModule,
    RouterModule,
    CommonModule,
    ChatbotComponent
  ],
  templateUrl: './virement.component.html',
  standalone: true,
  styleUrl: './virement.component.css'
})
export class VirementComponent implements  OnInit{
  date=new Date();
  compteUser='';
  compteDestinataire= '';
  montant=0;
  motif='';
  comptes!:any;
  message='';

  constructor(private virementService: VirementService,private router: Router,private authService:AuthService, private compteService:CompteServiceService) {
  }
  ngOnInit():void{
    this.compteService.getComptesByClient().subscribe(data => {
      this.comptes = data;
      console.log('Les comptes:', this.comptes);
    });
  }

  onEffectueVirement() {
    const virementPayload = {
      compteUser: this.compteUser,
      compteDestinataire: this.compteDestinataire,
      montant: this.montant,
      motif: this.motif,
      date: new Date(this.date)
    };
    console.log('Payload envoyé:', virementPayload);
    this.virementService.initierVirement(virementPayload)
      .subscribe({
        next: (message: any) => {
          alert(message);
          this.router.navigate(['/code-validation-virement']);
        },

        error:err => {alert("Votre Solde est Invalide Ou Compte destinataire Introuvable ");
          this.router.navigate(['/virement'])}

      });
  }





}
