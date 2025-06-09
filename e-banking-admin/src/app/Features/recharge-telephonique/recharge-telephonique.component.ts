import { Component ,OnInit } from '@angular/core';
import {HeaderComponent} from '../../Components/header/header.component';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Router, RouterModule} from '@angular/router';
import {RechargeService} from '../../services/client_service/recharge.service';
import {AuthService} from '../../services/auth.service';
import {CompteServiceService} from '../../services/client_service/compte-service.service';
import {Compte} from '../../models/models-client/Compte';


@Component({
  selector: 'app-recharge-telephonique',
  imports: [
    HeaderComponent,
    FormsModule,
    RouterModule,
    CommonModule,
  ],
  templateUrl: './recharge-telephonique.component.html',
  standalone: true,
  styleUrl: './recharge-telephonique.component.css'
})
export class RechargeTelephoniqueComponent implements OnInit{
  compteUser= '';
  operatorName= '';
  phone='';
  montant=0;
  comptes!:Compte[];

  constructor(private rechargeService: RechargeService,private router: Router,private authService :AuthService , private  compteService :CompteServiceService) {
  }
  ngOnInit():void{
    this.compteService.getComptesByClient().subscribe(data => {
      this.comptes = data;
      console.log('Les comptes:', this.comptes);
    });
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
      next: (message: any) => {
        alert(message);
        this.router.navigate(['/code-validation-recharge']);
      }
      ,
      error: err => alert("Veuillez Verifier Votre Numero de Telephone")
    });
  }




}
