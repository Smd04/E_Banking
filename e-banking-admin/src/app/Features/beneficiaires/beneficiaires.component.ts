import { Component } from '@angular/core';
import {RouterModule} from '@angular/router';
import { ChatbotComponent } from '../../chatbot/chatbot.component';

@Component({
  selector: 'app-beneficiaires',
  imports: [
    RouterModule,ChatbotComponent
  ],
  templateUrl: './beneficiaires.component.html',
  standalone: true,
  styleUrl: './beneficiaires.component.css'
})
export class BeneficiairesComponent {

}
