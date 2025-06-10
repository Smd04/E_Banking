import { Component } from '@angular/core';
import {HeaderComponent} from '../../Components/header/header.component';
import {RouterModule} from '@angular/router';
import { ChatbotComponent } from '../../chatbot/chatbot.component';

@Component({
  selector: 'app-notification',
  imports: [
    HeaderComponent,
    RouterModule,
    ChatbotComponent
  ],
  templateUrl: './notification.component.html',
  standalone: true,
  styleUrl: './notification.component.css'
})
export class NotificationComponent {

}
