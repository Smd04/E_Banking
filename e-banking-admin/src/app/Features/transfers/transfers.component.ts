import { Component } from '@angular/core';
import {RouterModule} from '@angular/router';
import { ChatbotComponent } from '../../chatbot/chatbot.component';

@Component({
  selector: 'app-transfers',
  imports: [
    RouterModule,ChatbotComponent
  ],
  templateUrl: './transfers.component.html',
  styleUrl: './transfers.component.css'
})
export class TransfersComponent {

}
