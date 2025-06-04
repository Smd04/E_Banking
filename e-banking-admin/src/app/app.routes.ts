import { Routes } from '@angular/router';
import { DashboardComponent } from './admin/dashboard/dashboard.component';
import { CurrenciesComponent } from './admin/currencies/currencies.component';
import { SupportmessagesComponent } from './admin/supportmessages/supportmessages.component';
import { AdminsManagementComponent } from './admin/admins-management/admins-management.component';
import { UserListComponent } from './user-list/user-list.component';
import { ReferralComponent } from './referral/referral.component';
import { CryptoListComponent } from './crypto/crypto-list/crypto-list.component';
import { TransactionsComponent } from './crypto/transactions/transactions.component';

export const routes: Routes = [
    { path: '', component: DashboardComponent },
    { path: 'admin/currencies', component: CurrenciesComponent },
    { path: 'admin/supportmessages', component: SupportmessagesComponent},
    { path: 'admin/admins', component: AdminsManagementComponent},
    { path: 'admin', component: UserListComponent },
    { path: 'referral', component: ReferralComponent },
    { path: 'crypto', component: CryptoListComponent },
    { path: 'cryptotransactions', component: TransactionsComponent}

    // { path: 'accounts', component: AccountsComponent },
    // { path: 'login', component: LoginComponent },
    // { path: 'register', component: RegisterComponent }
];
