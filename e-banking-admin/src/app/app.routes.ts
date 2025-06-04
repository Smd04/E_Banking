import { Routes } from '@angular/router';
import { DashboardComponent } from './admin/dashboard/dashboard.component';
import { CurrenciesComponent } from './admin/currencies/currencies.component';
import { SupportmessagesComponent } from './admin/supportmessages/supportmessages.component';
import { AdminsManagementComponent } from './admin/admins-management/admins-management.component';
import { UserListComponent } from './user-list/user-list.component';
import { ReferralComponent } from './referral/referral.component';
import { CryptoListComponent } from './crypto/crypto-list/crypto-list.component';
import { TransactionsComponent } from './crypto/transactions/transactions.component';
import {CreateContractComponent} from './create-contract/create-contract.component';
import {EnrolClientComponent} from './enrol-client/enrol-client.component';
import {ClientListComponent} from './client-list/client-list.component';
import {AdminPanelComponent} from './admin-panel/admin-panel.component';
import { ContractListComponent } from './contract-list/contract-list.component';

export const routes: Routes = [
    { path: '', component: DashboardComponent },
    { path: 'admin/currencies', component: CurrenciesComponent },
    { path: 'admin/supportmessages', component: SupportmessagesComponent},
    { path: 'admin/admins', component: AdminsManagementComponent},
    { path: 'admin', component: UserListComponent },
    { path: 'referral', component: ReferralComponent },
    { path: 'crypto', component: CryptoListComponent },
    { path: 'cryptotransactions', component: TransactionsComponent},
    { path: 'create-contract', component: CreateContractComponent },
    { path: 'enrol-client', component: EnrolClientComponent },
    { path: 'client-list', component: ClientListComponent },
    { path: 'admin-panel', component: AdminPanelComponent },
    { path: 'contract-list', component: ContractListComponent }



    // { path: 'accounts', component: AccountsComponent },
    // { path: 'login', component: LoginComponent },
    // { path: 'register', component: RegisterComponent }
];
