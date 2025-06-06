import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Compte } from '../models/models-client/Compte';
import { BarcodeService } from '../services/client_service/barcode.service';
import { BiometricService } from '../services/client_service/biometric.service';
import { CompteServiceService } from '../services/client_service/compte-service.service';
import { QRPaimentDTO } from '../models/models-client/PaimentQECode';
import { RegisterCredentialRequest } from '../models/models-client/RegisterCredentialRequest';

@Component({
  selector: 'app-paimaent-qr-code',
  templateUrl: './paimaent-qr-code.component.html',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule
  ],
  styleUrls: ['./paimaent-qr-code.component.css']
})
export class PaimaentQrCodeComponent implements OnInit, OnDestroy {
  scannedData: string | null = null;
  paymentStatus: string | null = null;
  comptes: Compte[] = [];
  selectedCompte!: Compte;
  videoStream: MediaStream | null = null;
  scanInterval: any;
  qrSubscription?: Subscription;
  challenge!: string;
  isExist!: boolean;
  paiment!: QRPaimentDTO;
  register!: RegisterCredentialRequest;

  @ViewChild('videoElement') videoElement!: ElementRef<HTMLVideoElement>;

  constructor(
    private compteService: CompteServiceService,
    private barcodeService: BarcodeService,
    private biometricService: BiometricService
  ) {}

  ngOnInit(): void {
    this.compteService.getComptesByClient().subscribe(data => {
      this.comptes = data;
      console.log('Les comptes:', this.comptes);
    });

    this.qrSubscription = this.barcodeService.qrValue.subscribe(async value => {
      this.scannedData = value;
      console.log("scanned data", this.scannedData);

      if (this.scannedData) {
        this.stopCamera();

        const data = JSON.parse(this.scannedData);

        if (data.compteDest && data.montant) {
          this.paiment = {
            compteDest: data.compteDest,
            montant: data.montant,
            compteSource: this.selectedCompte.AccountNumber
          };
        } else {
          this.paiment = {
            compteDest: "ABCDEFGH",
            montant: 200,
            compteSource: this.selectedCompte.AccountNumber
          };
        }

        alert(`S'il vous plaît, vérifiez avec votre empreinte.`);
        console.log("Montant :", this.paiment.montant);

        this.biometricService.getChallengeFromBackend().subscribe(data => {
          this.challenge = data.challenge;
          this.isExist = data.exists;
          console.log("Challenge : ", this.challenge, " | Public key exist : ", this.isExist);

          if (this.isExist) {
            this.verifyWithWebAuthn(this.challenge);
          } else {
            this.registerWithWebAuthn(this.challenge);
          }
        });
      }
    });
  }

  async verifyWithWebAuthn(challenge: string) {
    const challengeBuffer = Uint8Array.from(
      atob(challenge.replace(/-/g, '+').replace(/_/g, '/')),
      c => c.charCodeAt(0)
    );

    const publicKey: PublicKeyCredentialRequestOptions = {
      challenge: challengeBuffer,
      timeout: 60000,
      userVerification: 'preferred'
    };

    try {
      const assertion = await navigator.credentials.get({ publicKey }) as PublicKeyCredential;

      const credentialId = btoa(String.fromCharCode(...new Uint8Array(assertion.rawId)));
      const clientDataJSON = btoa(
        String.fromCharCode(...new Uint8Array(assertion.response.clientDataJSON))
      );
      const signature = btoa(
        String.fromCharCode(...new Uint8Array((assertion.response as AuthenticatorAssertionResponse).signature))
      );

      const verifyRequest = {
        credentialId,
        challenge,
        clientDataJSON,
        signature
      };

      this.biometricService.verifyBiometric(verifyRequest).subscribe({
        next: (res) => {
          this.paymentStatus = 'Biométrie validée : ' + res;
          this.biometricService.EffectuerPaiment(this.paiment);
        },
        error: (err) => {
          this.paymentStatus = 'Échec de la vérification biométrique';
          console.error(err);
          this.biometricService.EffectuerPaiment(this.paiment);
        }
      });
    } catch (err) {
      this.paymentStatus = 'Erreur lors de la vérification';
      console.error('Erreur WebAuthn:', err);
    }
  }

  async onCompteSelected(): Promise<void> {
    if (this.selectedCompte) {
      setTimeout(() => {
        this.openCamera();
      }, 100);
    }
  }

  async openCamera(): Promise<void> {
    try {
      if (!this.videoElement) {
        console.error("Élément vidéo non trouvé !");
        return;
      }

      this.videoStream = await navigator.mediaDevices.getUserMedia({ video: { facingMode: 'environment' } });
      this.videoElement.nativeElement.srcObject = this.videoStream;
      this.videoElement.nativeElement.play();

      this.scanInterval = setInterval(() => {
        this.barcodeService.captureFrame(this.videoElement.nativeElement);
      }, 1000);
    } catch (error) {
      console.error('Erreur d’accès à la caméra:', error);
    }
  }

  stopCamera(): void {
    if (this.videoStream) {
      this.videoStream.getTracks().forEach(track => track.stop());
      this.videoStream = null;
    }
    if (this.scanInterval) {
      clearInterval(this.scanInterval);
      this.scanInterval = null;
    }
  }

  ngOnDestroy(): void {
    this.stopCamera();
    this.qrSubscription?.unsubscribe();
  }

  async registerWithWebAuthn(challenge: string) {
    console.log("Enregistrement de la clé publique...");

    const user = this.getUserFromToken();
    if (!user) {
      console.error("Erreur de récupération du token.");
      return;
    }

    const publicKey: PublicKeyCredentialCreationOptions = {
      challenge: this.base64UrlToUint8Array(challenge),
      rp: { name: 'SmartBank' },
      user: {
        id: this.strToUint8Array(user.id.toString()),
        name: user.username,
        displayName: user.displayName
      },
      pubKeyCredParams: [
        { type: "public-key", alg: -7 },   // ES256
        { type: "public-key", alg: -257 }  // RS256
      ],
      authenticatorSelection: {
        authenticatorAttachment: "platform",
        userVerification: "required"
      },
      timeout: 60000,
      attestation: 'direct'
    };

    try {
      const credential = await navigator.credentials.create({ publicKey }) as PublicKeyCredential;
      const attestationResponse = credential.response as AuthenticatorAttestationResponse;

      const registerData: RegisterCredentialRequest = {
        credentialId: credential.id,
        publicKey: this.arrayBufferToBase64(attestationResponse.attestationObject)
      };

      this.biometricService.registerCredetilal(registerData).subscribe({
        next: res => {
          alert("Enregistrement réussi !");
          this.biometricService.EffectuerPaiment(this.paiment);
        },
        error: err => {
          console.error("Erreur lors de l'enregistrement :", err);
          this.biometricService.EffectuerPaiment(this.paiment);
        }
      });
    } catch (error) {
      console.error("Erreur WebAuthn :", error);
      this.biometricService.EffectuerPaiment(this.paiment);
    }
  }

  getUserFromToken(): { id: string, username: string, displayName: string } | undefined {
    const token = localStorage.getItem('accessToken');
    if (!token) {
      console.error("Token non trouvé dans le localStorage");
      return;
    }

    const parts = token.split('.');
    if (parts.length !== 3) {
      console.error("Le token n'est pas au format JWT (header.payload.signature)");
      return;
    }

    try {
      const payloadRaw = this.base64UrlDecode(parts[1]);
      const payload = JSON.parse(payloadRaw);

      const id = payload.sub || '';
      const username = payload.username || '';
      const displayName = username || '';

      return { id, username, displayName };
    } catch (error) {
      console.error("Erreur lors du décodage du token :", error);
      return;
    }
  }

  arrayBufferToBase64(buffer: ArrayBuffer): string {
    let binary = '';
    const bytes = new Uint8Array(buffer);
    for (let i = 0; i < bytes.byteLength; i++) {
      binary += String.fromCharCode(bytes[i]);
    }
    return btoa(binary);
  }

  base64UrlToUint8Array(base64url: string): Uint8Array {
    const base64 = base64url
      .replace(/-/g, '+')
      .replace(/_/g, '/')
      .padEnd(Math.ceil(base64url.length / 4) * 4, '=');

    const binary = atob(base64);
    const bytes = new Uint8Array(binary.length);
    for (let i = 0; i < binary.length; i++) {
      bytes[i] = binary.charCodeAt(i);
    }
    return bytes;
  }

  base64UrlDecode(str: string): string {
    if (!str) throw new Error("Chaîne Base64URL invalide ou vide.");
    const base64 = str.replace(/-/g, '+').replace(/_/g, '/');
    const padded = base64.padEnd(base64.length + (4 - base64.length % 4) % 4, '=');
    return atob(padded);
  }

  strToUint8Array(str: string): Uint8Array {
    return new TextEncoder().encode(str);
  }
}