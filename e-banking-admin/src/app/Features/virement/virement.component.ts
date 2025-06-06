import { Component } from '@angular/core';
import {NgOptimizedImage} from '@angular/common';
import {HeaderComponent} from '../../Components/header/header.component';

import {Router, RouterModule} from '@angular/router';

import {FormsModule} from '@angular/forms';
import {VirementService} from '../../services/client_service/virement.service';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-virement',
  imports: [
    NgOptimizedImage,
    HeaderComponent,
    FormsModule,
    RouterModule,
  ],
  templateUrl: './virement.component.html',
  standalone: true,
  styleUrl: './virement.component.css'
})
export class VirementComponent {
  date=new Date();
  compteUser= '';
  compteDestinataire= '';
  montant=0;
  motif='';

  constructor(private virementService: VirementService,private router: Router,private authService:AuthService) {
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
        next: (data) => {
          alert(data);
        }
        });
    this.router.navigate(['/code-validation-virement']);
  }





}
