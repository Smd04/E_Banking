import {Component, OnInit} from '@angular/core';
import {BankCardComponent} from '../bank-card/bank-card.component';
import {NgForOf} from '@angular/common';
import {HeaderComponent} from '../../Components/header/header.component';
import {Card} from '../../models/models-client/Card';
import {CardService} from '../../services/client_service/card.service';
import {RouterModule} from '@angular/router';
import { ChatbotComponent } from '../../chatbot/chatbot.component';



@Component({
  selector: 'app-card-list',
  standalone: true,
  imports: [
    NgForOf,
    BankCardComponent,
    HeaderComponent,
    RouterModule,
    ChatbotComponent
  ],
  templateUrl: './card-list.component.html'
})
export class CardListComponent implements OnInit {
  cards: Card[] = [];

  constructor(private cardService: CardService) {}

  ngOnInit() {
    this.cardService.getCard().subscribe(data => {
      this.cards = data;
    });
  }
}



