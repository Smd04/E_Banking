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
import { ContractListComponent } from './contract-list/contract-list.component';
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





export const routes: Routes = [
   // { path: '', component: DashboardComponent },
    { path: 'admin/currencies', component: CurrenciesComponent },
    { path: 'admin/supportmessages', component: SupportmessagesComponent},
    { path: 'admin/admins', component: AdminsManagementComponent},
    { path: 'globalsettings', component: UserListComponent },
    { path: 'referral', component: ReferralComponent },
    { path: 'crypto', component: CryptoListComponent },
    { path: 'cryptotransactions', component: TransactionsComponent},
    { path: 'create-contract', component: CreateContractComponent },
    { path: 'enrol-client', component: EnrolClientComponent },
    { path: 'client-list', component: ClientListComponent },
    { path: 'contract-list', component: ContractListComponent },

    {path:'comptes' ,component: ComptesComponentComponent},
    {path:'detail-compte/:id' , component:CompteDetailComponent},
    {path:'transaction' , component:TransactionComponent},
    {path:'paiment-qr-code',component:PaimaentQrCodeComponent},
    {path:'' , component:LoginComponent},
    {path:"dashboard" ,component: DashboardComponent},
    {path:"virement", component: VirementComponent},
    {path:'recharge', component: RechargeTelephoniqueComponent},
    {path:'code-validation-virement',component:OtpInputVirementComponent},
    {path:'code-validation-recharge',component:OtpInputRechargeComponent},
    {path:'logout',component:LoginComponent},
    {path:'card',component:CardListComponent},
    {path:'facture',component:FactureComponent},
    {path:'abonnement',component:AbonnementComponent},
    {path:'support',component:SupportComponent},
    {path:'notification',component:NotificationComponent},
    {path:'historique-paiement',component:HistoriquePaiementComponent},
    {path:'historique-all-paiement',component:AllPaiementComponent},
    {path:'profile',component:ProfileComponent}
];
