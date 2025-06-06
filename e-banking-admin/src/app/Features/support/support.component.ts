import { Component } from '@angular/core';
import {HeaderComponent} from '../../Components/header/header.component';
import {RouterModule} from '@angular/router';

@Component({
  selector: 'app-support',
  imports: [
    HeaderComponent,
    RouterModule,
  ],
  templateUrl: './support.component.html',
  standalone: true,
  styleUrl: './support.component.css'
})
export class SupportComponent {

}
