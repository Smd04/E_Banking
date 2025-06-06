
import jspdf from 'jspdf';
import autoTable from 'jspdf-autotable';
import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule, DecimalPipe, NgClass} from '@angular/common';
import {Compte} from '../models/models-client/Compte';
import {Transaction} from '../models/models-client/Transaction';
import {TransactionService} from '../services/client_service/transaction.service';
import {CompteServiceService} from '../services/client_service/compte-service.service';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  imports: [
    CommonModule,
    FormsModule,
    NgClass,
    DecimalPipe
  ],
  styleUrls: ['./transaction.component.css']
})
export class TransactionComponent {

  comptes: Compte[] = [];
  transactions: Transaction[] = [];
  filtred: Transaction[] = [];

  compteSelectionne: string = '';
  typeFilter: string = '';
  searchTerm: string = '';
  dateDebut: string = '';
  dateFin: string = '';

  currentPage = 1;
  pageSize = 4;
  totalPages = 1;

  transactionActive !: Transaction;
  constructor(
    private transactionService: TransactionService,
    private compteService:CompteServiceService
  ) {}

  ngOnInit() {
    this.getCompteClient();
    this.getAllTransactions();
  }

  getAllTransactions() {

    this.transactionService.getTransactionsByUser().subscribe(data => {
      this.transactions = data;
      console.log("transactions",this.transactions);
      this.filterTransactions();
    });
  }

      getTransactionByAccount(accountId:string){
        console.log('ID sélectionné :', accountId);
    if(accountId){
    const id:number =  Number(accountId);
      this.transactionService.getTransactionByAccount(id).subscribe(data => {
        this.transactions =data;
       this.filterTransactions();

      })
    }
    else{
      this.getAllTransactions();
    }

      }
  getCompteClient() {
    this.compteService.getComptesByClient().subscribe(data => {
      this.comptes = data;
    });
  }



  filterTransactions() {
    let filtered = this.transactions;
    console.log("transaction ffff",filtered);

    if (this.typeFilter) {
     filtered = filtered.filter(t => t.type === this.typeFilter);
    }
    if (this.dateDebut) {
      const debut = new Date(this.dateDebut);
      filtered = filtered.filter(t => this.toDate(t.timestamp) >= debut);
    }

    if (this.dateFin) {
      const fin = new Date(this.dateFin);
      filtered = filtered.filter(t => this.toDate(t.timestamp) <=fin);

    }

    if (this.searchTerm) {
      const term = this.searchTerm.toLowerCase();
      filtered = filtered.filter(t =>
        t.status.toLowerCase().includes(term)
      );
    }

    this.filtred = filtered;
    console.log("ggggggg",filtered)
    this.totalPages = Math.ceil(this.filtred.length / this.pageSize);
    this.currentPage = 1;
  }

  get paginated() {
    console.log("paginated",this.filtred)
    const start = (this.currentPage - 1) * this.pageSize;
    return this.filtred.slice(start, start + this.pageSize);
  }

  nextPage() {
    if (this.currentPage < this.totalPages) this.currentPage++;
  }

  prevPage() {
    if (this.currentPage > 1) this.currentPage--;
  }

  voirDetails(transaction: Transaction) {
    this.transactionActive = transaction;
  }

  exportPDF() {
    const doc = new jspdf();
    doc.text('Historique des Transactions', 14, 10);
    const rows = this.filtred.map(t => [
      this.toDate(t.timestamp).toLocaleDateString(),
      t.type,
      t.description,
      (t.amount > 0 ? '+' : '-') + t.amount.toFixed(2) + ' MAD',

    ]);


    autoTable(doc, {
      head: [['Date', 'Type', 'Description', 'Montant', 'Solde']],
      body: rows
    });

    doc.save('transactions.pdf');
  }
  toDate(dateArray: [number, number, number, number?, number?]): Date {
    const [year, month, day, hour = 0, minute = 0] = dateArray;
    return new Date(year, month - 1, day, hour, minute);
  }
  toDateString(dateArray: [number, number, number, number?, number?]): string {
    const [year, month, day] = dateArray;
    const formattedMonth = month.toString().padStart(2, '0');
    const formattedDay = day.toString().padStart(2, '0');
    return `${year}-${formattedMonth}-${formattedDay}`;
  }

}

