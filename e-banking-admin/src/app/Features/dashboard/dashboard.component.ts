import {Component, OnInit} from '@angular/core';
import {CompteChartComponent} from '../../Components/Chart/compte-chart.component';
import {HeaderComponent} from '../../Components/header/header.component';
import {NavbarComponent} from '../../shared/navbar/navbar.component'
import {Observable} from 'rxjs';
import {AsyncPipe, DatePipe, NgClass, NgForOf} from '@angular/common';
import {DashboardService} from '../../services/client_service/dashboard.service';
import {Compte} from '../../models/models-client/Compte';
import {MonthlyBalance} from '../../models/models-client/MonthlyBalance';
import {Transaction} from '../../models/models-client/Transaction';
import {Route, Router, RouterModule} from '@angular/router';

@Component({
  selector: 'app-dashboard',
  imports: [
    CompteChartComponent,
    HeaderComponent,
    AsyncPipe,
    NgForOf,
    NgClass,
    RouterModule,
    NavbarComponent
  ],
  templateUrl: './dashboard.component.html',
  standalone: true,
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  constructor(private dashboardService:DashboardService,private router:Router) {
  }
  account : Observable<Compte> | undefined;
  monthlyBalance:Observable<MonthlyBalance> | undefined;
  recentTransaction:Transaction[] =[];
  chartData: { month: string, balance: number }[] = [];
  ngOnInit() {
    this.account = this.dashboardService.getComptePrincipalByToken();
    console.log(this.account);
    this.monthlyBalance = this.dashboardService.getMonthlyBalance();
    console.log("hshshhs"+this.monthlyBalance);
    this.dashboardService.getRecentTransaction().subscribe(
      data => {
        this.recentTransaction = data;
      }
    );
    this.dashboardService.getBalanceMonth().subscribe(data => {
      this.chartData = Object.entries(data).map(([key, value]) => ({
        month: key,
        balance: Number(value)
      }));
    });




  }
  redirectToTrenactions(){
    this.router.navigate(['/transaction']).then();
  }
  toDateString(dateArray: [number, number, number, number?, number?]): string {
    const [year, month, day] = dateArray;
    const formattedMonth = month.toString().padStart(2, '0');
    const formattedDay = day.toString().padStart(2, '0');
    return `${year}-${formattedMonth}-${formattedDay}`;
  }

}
