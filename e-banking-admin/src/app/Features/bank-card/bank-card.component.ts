import { Component, Input } from '@angular/core';
import {AsyncPipe, DatePipe, DecimalPipe, NgClass, NgForOf, UpperCasePipe} from '@angular/common';
import {HeaderComponent} from '../../Components/header/header.component';

import {Observable} from 'rxjs';
import {Card} from '../../models/models-client/Card';
import {RouterModule} from '@angular/router';

@Component({
  selector: 'app-bank-card',
  templateUrl: './bank-card.component.html',
  standalone: true,
  imports: [

    UpperCasePipe,
    DecimalPipe,


  RouterModule,

    DatePipe,
  ],
  styleUrls: ['./bank-card.component.css']
})
export class BankCardComponent {
  @Input() card!: Card;



}
