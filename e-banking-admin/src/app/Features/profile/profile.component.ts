// bank-profile.component.ts
import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {DatePipe, NgForOf, NgIf} from '@angular/common';
import {HeaderComponent} from '../../Components/header/header.component';

import {User} from '../../models/models-client/user';
import {Info} from '../../models/models-client/info';
import {ProfileService} from '../../services/client_service/Profile.service';

import { ChatbotComponent } from '../../chatbot/chatbot.component';

@Component({
  selector: 'app-bank-profile',
  templateUrl: './profile.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    HeaderComponent,
    ChatbotComponent
  ],
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  profileForm!: FormGroup;
  isEditing = false;
  isLoading = false;
  showSecuritySection = false;
  user:Info|undefined;
  constructor(private profileService:ProfileService) {

  }




  ngOnInit(): void {
    this.profileService.getUserByToken().subscribe(
      data=>{
        this.user=data;
      }
    )
  }
}
