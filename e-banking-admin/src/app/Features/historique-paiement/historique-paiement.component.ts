import { Component, OnInit } from '@angular/core';
import { DatePipe, NgForOf } from '@angular/common';
import { HeaderComponent } from '../../Components/header/header.component';
import { ListPaiementService } from '../../services/client_service/listPaiement.service';
import { Paiement } from '../../models/models-client/paiement';
import {Router, RouterModule} from '@angular/router';
import { ChatbotComponent } from '../../chatbot/chatbot.component';

@Component({
  selector: 'app-historique-paiement',
  imports: [
    DatePipe,
    HeaderComponent,
    NgForOf,
    RouterModule,
    ChatbotComponent
  ],
  templateUrl: './historique-paiement.component.html',
  standalone: true,
  styleUrls: ['./historique-paiement.component.css']
})
export class HistoriquePaiementComponent implements OnInit {
  recentPaiement: Paiement[] = [];

  constructor(private paiementService: ListPaiementService,private router:Router) {}

  ngOnInit() {
    this.paiementService.getRecentPaiement().subscribe(
      data => {
        this.recentPaiement = data;
      }
    );
  }
  toDateString(dateArray: [number, number, number, number?, number?]): string {
    const [year, month, day] = dateArray;
    const formattedMonth = month.toString().padStart(2, '0');
    const formattedDay = day.toString().padStart(2, '0');
    return `${year}-${formattedMonth}-${formattedDay}`;
  }
  getAllPaiement(){
    this.router.navigate(['/historique-all-paiement']).then();

  }
}
