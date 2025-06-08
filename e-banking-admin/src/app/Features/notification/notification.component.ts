import { Component } from '@angular/core';
import {HeaderComponent} from '../../Components/header/header.component';
import {RouterModule} from '@angular/router';

@Component({
  selector: 'app-notification',
  imports: [
    HeaderComponent,
    RouterModule,
  ],
  templateUrl: './notification.component.html',
  standalone: true,
  styleUrl: './notification.component.css'
})
export class NotificationComponent {

}
