import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {RegisterCredentialRequest} from '../../models/models-client/RegisterCredentialRequest';
import {VerifyRequest} from '../../models/models-client/VerifyRequest';
import {QRPaimentDTO} from '../../models/models-client/PaimentQECode';

@Injectable({
  providedIn: 'root'
})
export class BiometricService {
apiUrl:string = `http://localhost:8080/project_e_banking_war_exploded/api/biometric`;


  constructor(private http:HttpClient) {}


  getChallengeFromBackend(): Observable<{challenge:string,exists:boolean}> {
     return this.http.get<{ exists: boolean; challenge: string }>(`${this.apiUrl}/check-credential`,{
       withCredentials: true
     });
  }

  registerCredetilal(data :RegisterCredentialRequest):Observable<string>{
    return this.http.post(`${this.apiUrl}/register-credential`,data ,{
      responseType :"text"
    } );
  }
  verifyBiometric(data: VerifyRequest): Observable<string> {
    return this.http.post(`${this.apiUrl}/verify`, data, {
      responseType: 'text',
    });
  }
  EffectuerPaiment(paimentQRCode:QRPaimentDTO){
    console.log("montant", paimentQRCode.montant);
    console.log("compteDest",paimentQRCode.compteDest)

    alert(`paiment réussie  compte source,${paimentQRCode.compteSource},compte Des",${paimentQRCode.compteDest},montant,${paimentQRCode.montant}`);

    return this.http.post(`${this.apiUrl}/effectuer-paiment`,paimentQRCode);
   }
}

