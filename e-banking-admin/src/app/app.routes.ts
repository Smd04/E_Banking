import { Routes } from '@angular/router';
import { DashboardComponent } from './admin/dashboard/dashboard.component';
import { CurrenciesComponent } from './admin/currencies/currencies.component';
import { SupportmessagesComponent } from './admin/supportmessages/supportmessages.component';
import { AdminsManagementComponent } from './admin/admins-management/admins-management.component';
import { UserListComponent } from './admin/user-list/user-list.component';
import { ReferralComponent } from './referral/referral.component';
import { CryptoListComponent } from './crypto/crypto-list/crypto-list.component';
import { TransactionsComponent } from './crypto/transactions/transactions.component';
import {CreateContractComponent} from './create-contract/create-contract.component';
import {EnrolClientComponent} from './enrol-client/enrol-client.component';
import {ClientListComponent} from './client-list/client-list.component';
import {CompteDetailComponent} from './compte-detail/compte-detail.component';
import {ComptesComponentComponent} from './comptes-component/comptes-component.component';
import { TransactionComponent } from './transaction/transaction.component';
import { PaimaentQrCodeComponent } from './paimaent-qr-code/paimaent-qr-code.component';
import {LoginComponent} from './login/login.component';
import {OtpInputVirementComponent} from './Features/otp-input-virement/otp-input-virement.component';
import {OtpInputRechargeComponent} from './Features/otp-input-recharge/otp-input-recharge.component';
import {CardListComponent} from './Features/card-list/card-list.component';
import {FactureComponent} from './Features/facture/facture.component';
import {AbonnementComponent} from './Features/abonnement/abonnement.component';
import {SupportComponent} from './Features/support/support.component';
import {NotificationComponent} from './Features/notification/notification.component';
import {HistoriquePaiementComponent} from './Features/historique-paiement/historique-paiement.component';
import {AllPaiementComponent} from './Features/all-paiement/all-paiement.component';
import {ProfileComponent} from './Features/profile/profile.component';
import {VirementComponent} from './Features/virement/virement.component';
import {RechargeTelephoniqueComponent} from './Features/recharge-telephonique/recharge-telephonique.component';
import { DashboardComponent as dashboard } from './Features/dashboard/dashboard.component';
import {AccountComponent} from './Features/account/account.component'
import {AuthGuard} from './guards/auth.guard'
import { DashboardBanqueComponent } from './Banque/dashboard-banque/dashboard-banque.component';






export const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent  ,canActivate: [AuthGuard] },
  { path: 'admin/currencies', component: CurrenciesComponent ,canActivate: [AuthGuard]},
  { path: 'admin/supportmessages', component: SupportmessagesComponent,canActivate: [AuthGuard]},
  { path: 'admin/admins', component: AdminsManagementComponent ,canActivate: [AuthGuard]},
  { path: 'globalsettings', component: UserListComponent ,canActivate: [AuthGuard]},
  { path: 'referral', component: ReferralComponent,canActivate: [AuthGuard]},
  { path: 'crypto', component: CryptoListComponent ,canActivate: [AuthGuard]},
  { path: 'cryptotransactions', component: TransactionsComponent,canActivate: [AuthGuard]},
  { path: 'create-contract', component: CreateContractComponent,canActivate: [AuthGuard] },
  { path: 'enrol-client', component: EnrolClientComponent ,canActivate: [AuthGuard]},
  { path: 'client-list', component: ClientListComponent ,canActivate: [AuthGuard]},
  {path: 'account' , component:AccountComponent ,canActivate: [AuthGuard]},
  {path:'comptes' ,component: ComptesComponentComponent,canActivate: [AuthGuard]},
  {path:'detail-compte/:id' , component:CompteDetailComponent,canActivate: [AuthGuard]},
  {path:'transaction' , component:TransactionComponent ,canActivate: [AuthGuard]},
  {path:'paiment-qr-code',component:PaimaentQrCodeComponent,canActivate: [AuthGuard]},
  {path:'' , component:LoginComponent},
  {path:"dashboard-user" ,component: dashboard,canActivate: [AuthGuard]},
  {path:"virement", component: VirementComponent,canActivate: [AuthGuard]},
  {path:'recharge', component: RechargeTelephoniqueComponent,canActivate: [AuthGuard]},
  {path:'code-validation-virement',component:OtpInputVirementComponent,canActivate: [AuthGuard]},
  {path:'code-validation-recharge',component:OtpInputRechargeComponent,canActivate: [AuthGuard]},
  {path:'logout',component:LoginComponent,canActivate: [AuthGuard]},
  {path:'card',component:CardListComponent,canActivate: [AuthGuard]},
  {path:'facture',component:FactureComponent,canActivate: [AuthGuard]},
  {path:'abonnement',component:AbonnementComponent,canActivate: [AuthGuard]},
  {path:'support',component:SupportComponent,canActivate: [AuthGuard]},
  {path:'notification',component:NotificationComponent,canActivate: [AuthGuard]},
  {path:'historique-paiement',component:HistoriquePaiementComponent,canActivate: [AuthGuard]},
  {path:'historique-all-paiement',component:AllPaiementComponent,canActivate: [AuthGuard]},
  {path:'profile',component:ProfileComponent,canActivate: [AuthGuard]},
  {path:'dashboard-Banque', component:DashboardBanqueComponent,canActivate: [AuthGuard]}
];
