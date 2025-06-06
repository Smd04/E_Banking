import { Component } from '@angular/core';
import {HeaderComponent} from '../../Components/header/header.component';
import {NgOptimizedImage} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Router, RouterModule} from '@angular/router';
import {RechargeService} from '../../services/client_service/recharge.service';
import {AuthService} from '../../services/auth.service';


@Component({
  selector: 'app-recharge-telephonique',
  imports: [
    HeaderComponent,
    FormsModule,
    RouterModule,
  ],
  templateUrl: './recharge-telephonique.component.html',
  standalone: true,
  styleUrl: './recharge-telephonique.component.css'
})
export class RechargeTelephoniqueComponent {
  compteUser= '';
  operatorName= '';
  phone='';
  montant=0;

  constructor(private rechargeService: RechargeService,private router: Router,private authService :AuthService) {
  }
  codeOtp = '';
  showOtpInput = false;

  initier() {
    const rechargePayload = {
      compteUser: this.compteUser,
      operatorName: this.operatorName,
      phone: this.phone,
      montant: this.montant,
    };
    console.log(rechargePayload);
    this.rechargeService.initierRecharge(rechargePayload).subscribe({
      next: (data) => {
        console.log(data);
        this.showOtpInput = true;
        alert(data);
        this.router.navigate(['/code-validation-recharge']);
      },
      error: err => alert('Erreur : ' + err.message)
    });
  }




}
