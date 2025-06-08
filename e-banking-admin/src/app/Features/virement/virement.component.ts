import { Component ,OnInit } from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {HeaderComponent} from '../../Components/header/header.component';

import {Router, RouterModule} from '@angular/router';

import {FormsModule} from '@angular/forms';
import {VirementService} from '../../services/client_service/virement.service';
import {AuthService} from '../../services/auth.service';
import {CompteServiceService} from '../../services/client_service/compte-service.service';
import {Compte} from '../../models/models-client/Compte';

@Component({
  selector: 'app-virement',
  imports: [
    NgOptimizedImage,
    HeaderComponent,
    FormsModule,
    RouterModule,
    CommonModule,
  ],
  templateUrl: './virement.component.html',
  standalone: true,
  styleUrl: './virement.component.css'
})
export class VirementComponent implements  OnInit{
  date=new Date();
  compteUser!: Compte;
  compteDestinataire= '';
  montant=0;
  motif='';
  comptes!:any;

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
      compteUser: this.compteUser.accountNumber,
      compteDestinataire: this.compteDestinataire,
      montant: this.montant,
      motif: this.motif,
      date: new Date(this.date)
    };
    console.log('Payload envoyé:', virementPayload);
    this.virementService.initierVirement(virementPayload)
      .subscribe({
        next: (data) => {
          alert(data);
        }
        });
    this.router.navigate(['/code-validation-virement']);
  }





}
