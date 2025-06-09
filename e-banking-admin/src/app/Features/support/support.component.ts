import { Component } from '@angular/core';
import {HeaderComponent} from '../../Components/header/header.component';
import {RouterModule} from '@angular/router';

import {FormsModule} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {SupportService} from '../../services/client_service/Support.service';

@Component({
  selector: 'app-support',
  imports: [
    HeaderComponent,
    RouterModule,
    FormsModule,
  ],
  templateUrl: './support.component.html',
  standalone: true,
  styleUrl: './support.component.css'
})
export class SupportComponent {
  message = '';
  typeSupport='';

  constructor(private supportService: SupportService,private authService:AuthService) {
  }

  sendMessage() {
    const supportMessage={
      message:this.message,
      typeSupport:this.typeSupport
    }
    this.supportService.sendMessage(supportMessage).subscribe(
      {
        next: () => {
          console.log("all bien");
        }
      }




    );

  }
}
