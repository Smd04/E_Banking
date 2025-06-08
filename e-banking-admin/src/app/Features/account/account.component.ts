import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-account',
  imports: [],
  templateUrl: './account.component.html',
  styleUrl: './account.component.css'
})
export class AccountComponent implements OnInit{
user:any;
  constructor(private http:HttpClient){}
  ngOnInit(): void {
    this.getuserinfo();
  }
email=localStorage.getItem('email');

getuserinfo(){
  this.http.get(`http://localhost:8080/project_e_banking_war/api/admin/devise/account/${this.email}`)
}

}
